import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { loggedUser$ } from "../../state/logged-user.state";
import categoryService from "../../services/category.service";
import { Container, Table, Form, Button } from 'react-bootstrap';


const CategoriesList = () => {
  const [categories, setCategories] = useState([]);
  const [filteredCategories, setFilteredCategories] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [sortColumn, setSortColumn] = useState('');
  const [sortOrder, setSortOrder] = useState('asc');
  const [newCategory, setNewCategory] = useState({
    categoryId: '',
    categoryName: '',
  });
  const [editMode, setEditMode] = useState(false);
  const [editCategoryId, setEditCategoryId] = useState('');
  const [, setUser] = useState(null);

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
  }, [navigate]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await categoryService.getAll();
      setCategories(response.data);
      setFilteredCategories(response.data);
    } catch (error) {
      console.error('Error retrieving data:', error);
    }
  };

  const handleSearch = (e) => {
    const searchTerm = e.target.value;
    setSearchTerm(searchTerm);
    filterCategories(searchTerm);
  };

  const filterCategories = (searchTerm) => {
    const filtered = categories.filter(
      (category) =>
        category.categoryName.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredCategories(filtered);
  };

  const handleSort = (column) => {
    if (column === sortColumn) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortColumn(column);
      setSortOrder('asc');
    }

    const sortedCategories = [...filteredCategories].sort((a, b) => {
      const valueA = a[column];
      const valueB = b[column];

      if (valueA < valueB) {
        return sortOrder === 'asc' ? -1 : 1;
      }
      if (valueA > valueB) {
        return sortOrder === 'asc' ? 1 : -1;
      }
      return 0;
    });

    setFilteredCategories(sortedCategories);
  };

  const handleAddCategory = async () => {
    try {
      const response = await categoryService.create(newCategory);
      const newCategoryData = response.data;
      setCategories([...categories, newCategoryData]);
      setFilteredCategories([...categories, newCategoryData]);
      setNewCategory({
        categoryId: '',
        categoryName: '',
      });
    } catch (error) {
      console.error('Error adding category:', error);
    }
  };

  const handleEditCategory = (categoryId) => {
    setEditMode(true);
    setEditCategoryId(categoryId);
    const categoryToUpdate = categories.find((category) => category.categoryId === categoryId);
    setNewCategory(categoryToUpdate);
  };

  const handleUpdateCategory = async () => {
    try {
      const response = await categoryService.update(editCategoryId, newCategory);
      const updatedCategoryData = response.data;
      const updatedCategories = categories.map((category) => (category.categoryId === updatedCategoryData.categoryId ? updatedCategoryData : category));
      setCategories(updatedCategories);
      setFilteredCategories(updatedCategories);
      setNewCategory({
        categoryId: '',
        categoryName: '',
      });
      setEditCategoryId('');
      setEditMode(false);
    } catch (error) {
      console.error('Error updating category:', error);
    }
  };

  const handleDeleteCategory = async (categoryId) => {
    try {
      await categoryService.delete(categoryId);
      const updatedCategories = categories.filter((category) => category.categoryId !== categoryId);
      setCategories(updatedCategories);
      setFilteredCategories(updatedCategories);
    } catch (error) {
      console.error('Error deleting category:', error);
    }
  };

  const handleCancelEdit = () => {
    setNewCategory({
      categoryId: '',
      categoryName: '',
    });
    setEditCategoryId('');
    setEditMode(false);
  };

  return (
    <Container>
      <Form.Group>
        <Form.Control
          type="text"
          placeholder="Search Categories"
          value={searchTerm}
          onChange={handleSearch}
        />
      </Form.Group>

      <Table striped bordered>
        <thead>
          <tr>
            <th onClick={() => handleSort('categoryId')}>ID</th>
            <th onClick={() => handleSort('categoryName')}>Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredCategories.map((category) => (
            <tr key={category.categoryId}>
              <td>{category.categoryId}</td>
              <td>{category.categoryName}</td>
              <td>
                <Button
                  variant="outline-primary"
                  size="sm"
                  className="mr-2"
                  onClick={() => handleEditCategory(category.categoryId)}
                >
                  Edit
                </Button>
                <Button
                  variant="outline-danger"
                  size="sm"
                  onClick={() => handleDeleteCategory(category.categoryId)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Form.Group>
        <Form.Control
          type="text"
          placeholder="Enter category name"
          value={newCategory.categoryName}
          onChange={(e) =>
            setNewCategory({ ...newCategory, categoryName: e.target.value })
          }
        />
        {editMode ? (
          <>
            <Button variant="success" onClick={handleUpdateCategory}>
              Update Category
            </Button>
            <Button variant="secondary" onClick={handleCancelEdit} className="ml-2">
              Cancel
            </Button>
          </>
        ) : (
          <Button variant="primary" onClick={handleAddCategory} className="mt-2">
            Add Category
          </Button>
        )}
      </Form.Group>
    </Container>
  );
};

export default CategoriesList;