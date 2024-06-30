import { useState, useEffect } from 'react';
import { Form, Button, Table } from 'react-bootstrap';
import userService from '../../services/user.service';
import roleService from '../../services/role.service';
import { useNavigate } from 'react-router-dom';
import { loggedUser$ } from '../../state/logged-user.state';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]); // State to manage roles
  const [selectedRoles, setSelectedRoles] = useState(''); // State to manage selected roles
  const [filteredItems, setFilteredItems] = useState([]);
  const [sortField, setSortField] = useState('');
  const [sortDirection, setSortDirection] = useState('asc');
  const [editMode, setEditMode] = useState(false);
  const [editItemId, setEditItemId] = useState('');
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [userEmail, setUserEmail] = useState('');

  const [user, setUser] = useState(null);

  const navigate = useNavigate();
  
  useEffect(() => {
    const subscription = loggedUser$.subscribe(_user => {
      if (!_user.userId) {
        navigate('/');
      } else if (_user.role !== 'ADMIN') {
        navigate('/');
      }
      setUser(_user);
    });

    return () => {
      subscription.unsubscribe();
    };
  }, []);

  useEffect(() => {
    fetchUsers();
    fetchRoles(); // Fetch roles when the component mounts
  }, []);

  useEffect(() => {
    setFilteredItems([...users]);
  }, [users]);

  const fetchUsers = async () => {
    try {
      const response = await userService.getAll();
      setUsers(response.data);
    } catch (error) {
      console.error('Failed to fetch users:', error);
    }
  };

  const fetchRoles = async () => {
    try {
      const response = await roleService.getAll();
      setRoles(response.data);
    } catch (error) {
      console.error('Failed to fetch roles:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Create a new user
    const newUser = {
      firstname,
      lastname,
      phoneNumber,
      userEmail,
      roles: selectedRoles ? [{ roleId: selectedRoles }] : [], // Include selected roles
    };

    try {
      // Call the endpoint to add a new user
      await userService.create(newUser);
      setUsers([...users, newUser]);
      setFirstname('');
      setLastname('');
      setPhoneNumber('');
      setUserEmail('');
      setSelectedRoles(''); // Clear selected roles
    } catch (error) {
      console.error('Failed to add user:', error);
    }
  };

  const handleSort = (field) => {
    if (field === sortField) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('asc');
    }
  };

  const handleEdit = (itemId) => {
    setEditMode(true);
    setEditItemId(itemId);
    const itemToEdit = users.find((item) => item.userId === itemId);
    setFirstname(itemToEdit.firstname);
    setLastname(itemToEdit.lastname);
    setPhoneNumber(itemToEdit.phoneNumber);
    setUserEmail(itemToEdit.userEmail);
    setSelectedRoles(itemToEdit.roles.length > 0 ? itemToEdit.roles[0].roleId : ''); // Set selected roles
  };

  const handleUpdate = async () => {
    const itemToUpdate = users.find((item) => item.userId === editItemId);

    // Create an updated user object
    const updatedUser = {
      ...itemToUpdate,
      firstname,
      lastname,
      phoneNumber,
      userEmail,
      roles: selectedRoles ? [{ roleId: selectedRoles }] : [], // Include selected roles
    };

    try {
      // Call the endpoint to update the user
      await userService.update(updatedUser.userId, updatedUser);
      const updatedItems = [...users];
      const itemIndex = updatedItems.findIndex((item) => item.userId === editItemId);
      updatedItems[itemIndex] = updatedUser;
      setUsers(updatedItems);
      setEditMode(false);
      setEditItemId('');
      setFirstname('');
      setLastname('');
      setPhoneNumber('');
      setUserEmail('');
      setSelectedRoles(''); // Clear selected roles
    } catch (error) {
      console.error('Failed to update user:', error);
    }
  };

  const handleCancel = () => {
    setEditMode(false);
    setEditItemId('');
    setFirstname('');
    setLastname('');
    setPhoneNumber('');
    setUserEmail('');
    setSelectedRoles(''); // Clear selected roles
  };

  const handleDelete = async (index) => {
    const userIdToDelete = filteredItems[index].userId;

    try {
      // Call the endpoint to delete the user
      await userService.delete(userIdToDelete);
      const updatedItems = users.filter((item) => item.userId !== userIdToDelete);
      setUsers(updatedItems);
      setFilteredItems(updatedItems);
    } catch (error) {
      console.error('Failed to delete user:', error);
    }
  };

  const sortedItems = [...filteredItems].sort((a, b) => {
    const fieldValueA = a[sortField]?.toString().toLowerCase() || '';
    const fieldValueB = b[sortField]?.toString().toLowerCase() || '';

    if (fieldValueA < fieldValueB) {
      return sortDirection === 'asc' ? -1 : 1;
    }
    if (fieldValueA > fieldValueB) {
      return sortDirection === 'asc' ? 1 : -1;
    }
    return 0;
  });

  // const handleRoleChange = (e) => {
  //   const selectedOptions = Array.from(e.target.selectedOptions, option => option.value);
  //   setSelectedRoles(selectedOptions);
  // };

  return (
    <div>
    <Table>
      <thead>
        <tr>
          <th onClick={() => handleSort('firstname')}>First Name</th>
          <th onClick={() => handleSort('lastname')}>Last Name</th>
          <th onClick={() => handleSort('phoneNumber')}>Phone Number</th>
          <th onClick={() => handleSort('userEmail')}>Email</th>
          <th onClick={() => handleSort('roles')}>Roles</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {sortedItems.map((item, index) => (
          <tr key={item.userId}>
            <td>{item.firstname}</td>
            <td>{item.lastname}</td>
            <td>{item.phoneNumber}</td>
            <td>{item.userEmail}</td>
            <td>{item.roles.map(role => role.roleName).join(', ')}</td>
            <td>
              <Button variant="primary" onClick={() => handleEdit(item.userId)}>
                Edit
              </Button>
              <Button variant="danger" onClick={() => handleDelete(index)}>
                Delete
              </Button>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>

    <Form onSubmit={handleSubmit}>
      <Form.Group>
        <Form.Label>First Name:</Form.Label>
        <Form.Control type="text" value={firstname} onChange={(e) => setFirstname(e.target.value)} required />
      </Form.Group>

      <Form.Group>
        <Form.Label>Last Name:</Form.Label>
        <Form.Control type="text" value={lastname} onChange={(e) => setLastname(e.target.value)} required />
      </Form.Group>

      <Form.Group>
        <Form.Label>Phone Number:</Form.Label>
        <Form.Control type="text" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} required />
      </Form.Group>

      <Form.Group>
        <Form.Label>Email:</Form.Label>
        <Form.Control type="email" value={userEmail} onChange={(e) => setUserEmail(e.target.value)} required />
      </Form.Group>

      <Form.Group>
        <Form.Label>Role:</Form.Label>
        <Form.Control as="select" value={selectedRoles} onChange={(e) => setSelectedRoles(e.target.value)} required>
          <option value="">Select Role</option>
          {roles.map(role => (
            <option key={role.roleId} value={role.roleId}>{role.roleName}</option>
          ))}
        </Form.Control>
      </Form.Group>

      {editMode ? (
        <div>
          <Button variant="primary" onClick={handleUpdate}>
            Update
          </Button>
          <Button variant="secondary" onClick={handleCancel}>
            Cancel
          </Button>
        </div>
      ) : (
        <Button type="submit">Add User</Button>
      )}
    </Form>
  </div>
    // <div>
    //   <Form onSubmit={handleSubmit}>
    //     <Form.Group>
    //       <Form.Label>First Name:</Form.Label>
    //       <Form.Control type="text" value={firstname} onChange={(e) => setFirstname(e.target.value)} required />
    //     </Form.Group>

    //     <Form.Group>
    //       <Form.Label>Last Name:</Form.Label>
    //       <Form.Control type="text" value={lastname} onChange={(e) => setLastname(e.target.value)} required />
    //     </Form.Group>

    //     <Form.Group>
    //       <Form.Label>Phone Number:</Form.Label>
    //       <Form.Control type="text" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} required />
    //     </Form.Group>

    //     <Form.Group>
    //       <Form.Label>Email:</Form.Label>
    //       <Form.Control type="email" value={userEmail} onChange={(e) => setUserEmail(e.target.value)} required />
    //     </Form.Group>

    //     <Form.Group>
    //       <Form.Label>Roles:</Form.Label>
    //       <Form.Control as="select" multiple value={selectedRoles} onChange={handleRoleChange} required>
    //         {roles.map(role => (
    //           <option key={role.roleId} value={role.roleId}>{role.roleName}</option>
    //         ))}
    //       </Form.Control>
    //     </Form.Group>

    //     {editMode ? (
    //       <div>
    //         <Button variant="primary" onClick={handleUpdate}>
    //           Update
    //         </Button>
    //         <Button variant="secondary" onClick={handleCancel}>
    //           Cancel
    //         </Button>
    //       </div>
    //     ) : (
    //       <Button type="submit">Add User</Button>
    //     )}
    //   </Form>

    //   <Table>
    //     <thead>
    //       <tr>
    //         <th onClick={() => handleSort('firstname')}>First Name</th>
    //         <th onClick={() => handleSort('lastname')}>Last Name</th>
    //         <th onClick={() => handleSort('phoneNumber')}>Phone Number</th>
    //         <th onClick={() => handleSort('userEmail')}>Email</th>
    //         <th onClick={() => handleSort('roles')}>Roles</th>
    //         <th>Actions</th>
    //       </tr>
    //     </thead>
    //     <tbody>
    //       {sortedItems.map((item, index) => (
    //         <tr key={item.userId}>
    //           <td>{item.firstname}</td>
    //           <td>{item.lastname}</td>
    //           <td>{item.phoneNumber}</td>
    //           <td>{item.userEmail}</td>
    //           <td>{item.roles.map(role => role.roleName).join(', ')}</td>
    //           <td>
    //             <Button variant="primary" onClick={() => handleEdit(item.userId)}>
    //               Edit
    //             </Button>
    //             <Button variant="danger" onClick={() => handleDelete(index)}>
    //               Delete
    //             </Button>
    //           </td>
    //         </tr>
    //       ))}
    //     </tbody>
    //   </Table>
    // </div>
  );
};

export default UserList;
