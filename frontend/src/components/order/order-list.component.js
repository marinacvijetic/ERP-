// import React, { useState, useEffect } from 'react';
// import { Table, FormControl } from 'react-bootstrap';
// import orderService from '../../services/order.service';
// import orderStatusService from '../../services/orderStatus.service';
// import arrivalDetailsService from '../../services/orderArrival.service';
// import { useNavigate } from 'react-router-dom';
// import { loggedUser$ } from '../../state/logged-user.state';

// const OrderList = () => {
//   const [orders, setOrders] = useState([]);
//   const [filteredOrders, setFilteredOrders] = useState([]);
//   const [orderStatuses, setOrderStatuses] = useState([]);
//   const [arrivalDetails, setArrivalDetails] = useState([]);
//   const [sortField, setSortField] = useState('');
//   const [sortOrder, setSortOrder] = useState('asc');
//   const [searchTerm, setSearchTerm] = useState('');

//   const [user, setUser] = useState(null);

//   const navigate = useNavigate();
//   useEffect(() => {
//     const subscription = loggedUser$.subscribe(_user => {
//         if (!_user.userId) {
//             navigate('/');
//         } else if (_user.role !== 'ADMIN') {
//             navigate('/');
//         }

//       setUser(_user);
//     });

//     return () => {
//       subscription.unsubscribe();
//     };
//   }, [navigate]);

//   useEffect(() => {
//     fetchOrders();
//     fetchOrderStatuses();
//     fetchArrivalDetails();
//   }, []);

//   const fetchOrders = async () => {
//     try {
//       const response = await orderService.getAll();
//       setOrders(response.data);
//       setFilteredOrders(response.data);
//     } catch (error) {
//       console.error('Failed to fetch orders:', error);
//     }
//   };

//   const fetchOrderStatuses = async () => {
//     try {
//       const response = await orderStatusService.getAll();
//       setOrderStatuses(response.data);
//     } catch (error) {
//       console.error('Failed to fetch order statuses:', error);
//     }
//   };

//   const fetchArrivalDetails = async () => {
//     try {
//       const response = await arrivalDetailsService.getAll();
//       setArrivalDetails(response.data);
//     } catch (error) {
//       console.error('Failed to fetch arrival details:', error);
//     }
//   };

//   const handleSort = (field) => {
//     if (field === sortField) {
//       setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
//     } else {
//       setSortField(field);
//       setSortOrder('asc');
//     }
//   };

//   const handleSearch = (e) => {
//     const searchTerm = e.target.value.toLowerCase();
//     setSearchTerm(searchTerm);

//     const filteredOrders = orders.filter((order) => {
//       return (
//         order.user.firstname.toLowerCase().includes(searchTerm) ||
//         order.user.lastname.toLowerCase().includes(searchTerm)
//       );
//     });

//     setFilteredOrders(filteredOrders);
//   };

//   const sortedOrders = filteredOrders.sort((a, b) => {
//     const aValue = a[sortField];
//     const bValue = b[sortField];

//     if (sortOrder === 'asc') {
//       return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
//     } else {
//       return bValue < aValue ? -1 : bValue > aValue ? 1 : 0;
//     }
//   });

//   const handleStatusUpdate = async (orderId, newStatusId) => {
//     try {
//       const newOrder = orders.find(x => x.orderId === orderId);
//       newOrder.statusId = newStatusId;
//       await orderService.update(orderId, newOrder);
//       fetchOrders();
//     } catch (error) {
//       console.error('Failed to update status:', error);
//     }
//   };

//   const handleArrivalDetailsUpdate = async (orderId, newArrivalDetailsId) => {
//     try {
//       const newOrder = orders.find(x => x.orderId === orderId);
//       newOrder.arrivalDetails.arrivalDetailsId = newArrivalDetailsId;
//       await orderService.update(orderId, newOrder);
//       fetchOrders();
//     } catch (error) {
//       console.error('Failed to update arrival details:', error);
//     }
//   };

//   const handleShippingMethodUpdate = async (orderId, newShippingMethod) => {
//     try {
//       const newOrder = orders.find(x => x.orderId === orderId);
//       newOrder.shippingMethod = newShippingMethod;
//       await orderService.update(orderId, newOrder);
//       fetchOrders();
//     } catch (error) {
//       console.error('Failed to update shipping method:', error);
//     }
//   };

//   return (
//     <div>
//       <div className="mb-3">
//         <FormControl
//           type="text"
//           placeholder="Search by name or last name"
//           value={searchTerm}
//           onChange={handleSearch}
//         />
//       </div>
//       <Table>
//         <thead>
//           <tr>
//             <th onClick={() => handleSort('orderDate')}>Order Date</th>
//             <th onClick={() => handleSort('status')}>Status</th>
//             <th onClick={() => handleSort('total')}>Total</th>
//             <th onClick={() => handleSort('user.name')}>User</th>
//             <th>Items</th>
//             <th>Arrival Details</th>
//             <th>Shipping Method</th>
//           </tr>
//         </thead>
//         <tbody>
//           {sortedOrders.map((order) => (
//             <tr key={order.orderId}>
//               <td>{order.orderDate}</td>
//               <td>
//                 <select
//                   value={order.status.id}
//                   onChange={(e) => handleStatusUpdate(order.orderId, e.target.value)}
//                 >
//                   {orderStatuses.map((status) => (
//                     <option key={status.id} value={status.id}>
//                       {status.name}
//                     </option>
//                   ))}
//                 </select>
//               </td>
//               <td>{order.total}</td>
//               <td>{order.user.firstname} {order.user.lastname}</td>
//               <td>
//                 <ul>
//                   {order.orderItems && order.orderItems.map((item, idx) => (
//                     <li key={idx}>
//                       <span className="item-name">{item.name}</span>
//                       <span className="item-details">
//                         Quantity: {item.quantity} | Total Price: {item.totalPrice}
//                       </span>
//                     </li>
//                   ))}
//                 </ul>
//               </td>
//               <td>
//                 <select
//                   value={order.arrivalDetails.arrivalDetailsId}
//                   onChange={(e) => handleArrivalDetailsUpdate(order.orderId, e.target.value)}
//                 >
//                   {arrivalDetails.map((detail) => (
//                     <option key={detail.arrivalDetailsId} value={detail.arrivalDetailsId}>
//                       {`${detail.country}, ${detail.city}, ${detail.streetName} ${detail.streetNumber}, ${detail.arrivalDate} ${detail.arrivalTime}`}
//                     </option>
//                   ))}
//                 </select>
//               </td>
//               <td>
//                 <select
//                   value={order.shippingMethod}
//                   onChange={(e) => handleShippingMethodUpdate(order.orderId, e.target.value)}
//                 >
//                   <option value="TAKEAWAY">TAKE AWAY</option>
//                   <option value="DELIVERY">DELIVERY</option>
//                 </select>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </Table>
//     </div>
//   );
// };

// export default OrderList;
// import React, { useState, useEffect } from 'react';
// import { Table, FormControl } from 'react-bootstrap';
// import orderService from '../../services/order.service';
// import { useNavigate } from 'react-router-dom';
// import { loggedUser$ } from '../../state/logged-user.state';

// import React, { useState, useEffect } from 'react';
// import { Table, Form, Button, Container, Row, Col, InputGroup } from 'react-bootstrap';
// import orderService from '../../services/order.service';
// import orderStatusService from '../../services/orderStatus.service';
// import arrivalDetailsService from '../../services/orderArrival.service';
// import { useNavigate } from 'react-router-dom';
// import { loggedUser$ } from '../../state/logged-user.state';

// const OrderList = () => {
//   const [orders, setOrders] = useState([]);
//   const [filteredOrders, setFilteredOrders] = useState([]);
//   const [orderStatuses, setOrderStatuses] = useState([]);
//   const [arrivalDetailsOptions, setArrivalDetailsOptions] = useState([]);
//   const [sortField, setSortField] = useState('');
//   const [sortOrder, setSortOrder] = useState('asc');
//   const [searchTerm, setSearchTerm] = useState('');
//   const [user, setUser] = useState(null);
//   const [editMode, setEditMode] = useState({});
//   const [editedOrderData, setEditedOrderData] = useState({});

//   const navigate = useNavigate();

//   useEffect(() => {
//     const subscription = loggedUser$.subscribe(_user => {
//       if (!_user.userId || _user.role !== 'ADMIN') {
//         navigate('/');
//       } else {
//         setUser(_user);
//       }
//     });

//     return () => {
//       subscription.unsubscribe();
//     };
//   }, [navigate]);

//   useEffect(() => {
//     fetchOrders();
//     fetchOrderStatuses();
//     fetchArrivalDetails();
//   }, []);

//   const fetchOrders = async () => {
//     try {
//       const response = await orderService.getAll();
//       setOrders(response.data);
//       setFilteredOrders(response.data);
//     } catch (error) {
//       console.error('Failed to fetch orders:', error);
//     }
//   };

//   const fetchOrderStatuses = async () => {
//     try {
//       const response = await orderStatusService.getAll();
//       setOrderStatuses(response.data);
//     } catch (error) {
//       console.error('Failed to fetch order statuses:', error);
//     }
//   };

//   const fetchArrivalDetails = async () => {
//     try {
//       const response = await arrivalDetailsService.getAll();
//       setArrivalDetailsOptions(response.data);
//     } catch (error) {
//       console.error('Failed to fetch arrival details:', error);
//     }
//   };

//   const handleSearch = (event) => {
//     const value = event.target.value.toLowerCase();
//     setSearchTerm(value);
//     const filtered = orders.filter(order =>
//       order.user.firstname.toLowerCase().includes(value) ||
//       order.user.lastname.toLowerCase().includes(value) ||
//       order.orderItemsItems.some(item => item.product.productName.toLowerCase().includes(value))
//     );
//     setFilteredOrders(filtered);
//   };

//   const handleSort = (field) => {
//     const order = sortOrder === 'asc' ? 'desc' : 'asc';
//     const sorted = [...filteredOrders].sort((a, b) => {
//       if (a[field] < b[field]) return sortOrder === 'asc' ? -1 : 1;
//       if (a[field] > b[field]) return sortOrder === 'asc' ? 1 : -1;
//       return 0;
//     });
//     setSortField(field);
//     setSortOrder(order);
//     setFilteredOrders(sorted);
//   };

//   const toggleEditMode = (orderId) => {
//     setEditMode((prevEditMode) => ({
//       ...prevEditMode,
//       [orderId]: !prevEditMode[orderId]
//     }));
//     if (!editMode[orderId]) {
//       const order = orders.find(order => order.orderId === orderId);
//       setEditedOrderData({
//         status: {statusId: order.status.statusId},
//         arrivalDetailsId: {arrivalDetailsId: order.arrivalDetails.arrivalDetailsId}
//       });
//     }
//   };

//   const handleStatusChange = (orderId, statusId) => {
//     setEditedOrderData((prevData) => ({
//       ...prevData,
//       status: {statusId: parseInt(statusId)}
//     }));
//   };

//   const handleArrivalDetailsChange = (orderId, arrivalDetailsId) => {
//     setEditedOrderData((prevData) => ({
//       ...prevData,
//       arrivalDetails: {arrivalDetailsId: parseInt(arrivalDetailsId)}
//     }));
//   };

//   const saveChanges = async (orderId) => {
//     try {
//       await orderStatusService.update(orderId, editedOrderData.status);
//       await arrivalDetailsService.update(orderId, editedOrderData.arrivalDetails);
//       fetchOrders();
//       toggleEditMode(orderId);
//     } catch (error) {
//       console.error('Failed to save changes:', error);
//     }
//   };

//   return (
//     <Container>
//       <h1 className="mt-4 mb-4">Order List</h1>
//       <InputGroup className="mb-3">
//         <Form.Control
//           type="text"
//           placeholder="Search by customer name or product"
//           value={searchTerm}
//           onChange={handleSearch}
//         />
//       </InputGroup>
//       <Table striped bordered hover responsive>
//         <thead>
//           <tr>
//             <th onClick={() => handleSort('orderId')}>Order ID</th>
//             <th onClick={() => handleSort('orderDate')}>Order Date</th>
//             <th onClick={() => handleSort('status.orderStatus')}>Status</th>
//             <th onClick={() => handleSort('total')}>Total</th>
//             <th onClick={() => handleSort('user.firstname')}>Customer</th>
//             <th>Order Items</th>
//             <th>Arrival Details</th>
//             <th onClick={() => handleSort('shippingMethod')}>Shipping Method</th>
//             <th>Actions</th>
//           </tr>
//         </thead>
//         <tbody>
//           {filteredOrders.map(order => (
//             <tr key={order.orderId}>
//               <td>{order.orderId}</td>
//               <td>{order.orderDate}</td>
//               <td>
//                 {editMode[order.orderId] ? (
//                   <Form.Control
//                     as="select"
//                     value={editedOrderData.statusId}
//                     onChange={(e) => handleStatusChange(order.orderId, e.target.value)}
//                   >
//                     {orderStatuses.map(status => (
//                       <option key={status.statusId} value={status.statusId}>
//                         {status.orderStatus}
//                       </option>
//                     ))}
//                   </Form.Control>
//                 ) : (
//                   order.status.orderStatus
//                 )}
//               </td>
//               <td>{order.total}</td>
//               <td>{order.user.firstname} {order.user.lastname}</td>
//               <td>
//                 <ul className="list-unstyled">
//                   {order.orderItemsItems.map((item, idx) => (
//                     <li key={idx}>
//                       <strong>{item.product.productName}</strong> - Quantity: {item.quantity}
//                     </li>
//                   ))}
//                 </ul>
//               </td>
//               <td>
//                 {editMode[order.orderId] ? (
//                   <Form.Control
//                     as="select"
//                     value={editedOrderData.arrivalDetails.arrivalDetailsId}
//                     onChange={(e) => handleArrivalDetailsChange(order.orderId, e.target.value)}
//                   >
//                     {arrivalDetailsOptions.map(detail => (
//                       <option key={detail.arrivalDetailsId} value={detail.arrivalDetailsId}>
//                         {`${detail.country}, ${detail.city}, ${detail.streetName} ${detail.streetNumber}, ${detail.arrivalDate} ${detail.arrivalTime}`}
//                       </option>
//                     ))}
//                   </Form.Control>
//                 ) : (
//                   <div>
//                     <div><strong>Date:</strong> {order.arrivalDetails.arrivalDate}</div>
//                     <div><strong>Time:</strong> {order.arrivalDetails.arrivalTime}</div>
//                     <div><strong>Country:</strong> {order.arrivalDetails.country}</div>
//                     <div><strong>City:</strong> {order.arrivalDetails.city}</div>
//                     <div><strong>Street:</strong> {order.arrivalDetails.streetName} {order.arrivalDetails.streetNumber}</div>
//                   </div>
//                 )}
//               </td>
//               <td>{order.shippingMethod}</td>
//               <td>
//                 {editMode[order.orderId] ? (
//                   <Button variant="success" size="sm" onClick={() => saveChanges(order.orderId)}>
//                     Save
//                   </Button>
//                 ) : (
//                   <Button variant="primary" size="sm" onClick={() => toggleEditMode(order.orderId)}>
//                     Edit
//                   </Button>
//                 )}
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </Table>
//     </Container>
//   );
// };

// export default OrderList;

import { useEffect, useState } from 'react';
import { Button, Container, Form, Table } from 'react-bootstrap';
import orderService from '../../services/order.service';
import statusService from '../../services/orderStatus.service';
import { loggedUser$ } from '../../state/logged-user.state';


const OrderList = () => {
  const [orders, setOrders] = useState([]);
  const [statuses, setStatuses] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredOrders, setFilteredOrders] = useState([]);
  const [editMode, setEditMode] = useState(false);
  const [editOrderId, setEditOrderId] = useState('');
  const [updatedOrder, setUpdatedOrder] = useState({
    orderId: '',
    status: { statusId: '', orderStatus: '' }
  });
  const [user, setUser] = useState(null);

  useEffect(() => {
    const subscription = loggedUser$.subscribe(_user => {
      setUser(_user);
    });

    return () => {
      subscription.unsubscribe();
    };
  }, []);

  useEffect(() => {
    fetchOrders();
    fetchStatuses();
  }, []);

  const fetchOrders = async () => {
    try {
      const response = await orderService.getAll();
      setOrders(response.data);
      setFilteredOrders(response.data);
    } catch (error) {
      console.error('Error retrieving orders:', error);
    }
  };

  const fetchStatuses = async () => {
    try {
      const response = await statusService.getAll();
      setStatuses(response.data);
    } catch (error) {
      console.error('Error retrieving statuses:', error);
    }
  };

  const handleSearch = (e) => {
    const searchTerm = e.target.value;
    setSearchTerm(searchTerm);
    filterOrders(searchTerm);
  };

  const filterOrders = (searchTerm) => {
    const filtered = orders.filter(
      (order) =>
        order.orderId.toString().includes(searchTerm) ||
        order.status.orderStatus.toLowerCase().includes(searchTerm.toLowerCase()) ||
        order.arrivalDetails.country.toLowerCase().includes(searchTerm.toLowerCase()) ||
        order.user.fullName.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredOrders(filtered);
  };

  const handleEditOrder = (orderId) => {
    setEditMode(true);
    setEditOrderId(orderId);
    const orderToUpdate = orders.find((order) => order.orderId === orderId);
    setUpdatedOrder(orderToUpdate);
  };

  const handleUpdateOrder = async () => {
    try {
      await orderService.update(editOrderId, updatedOrder);
      const updatedOrders = orders.map((order) => (order.orderId === editOrderId ? updatedOrder : order));
      setOrders(updatedOrders);
      setFilteredOrders(updatedOrders);
      setEditOrderId('');
      setEditMode(false);
      setUpdatedOrder({
        orderId: '',
        status: { statusId: '', orderStatus: '' },
      });
    } catch (error) {
      console.error('Error updating order:', error);
    }
  };

  const handleCancelEdit = () => {
    setEditOrderId('');
    setEditMode(false);
    setUpdatedOrder({
      orderId: '',
      status: { statusId: '', orderStatus: '' },
    });
  };

  return (
    <Container>
      <Form.Group>
        <Form.Control
          type="text"
          placeholder="Search Orders"
          value={searchTerm}
          onChange={handleSearch}
        />
      </Form.Group>
      <Table striped bordered>
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Total</th>
            <th>Shipping Method</th>
            <th>Order Items</th>
            <th>Arrival Details</th>
            <th>User</th>
            {user && user.role === 'ADMIN' && <th>Actions</th>}
          </tr>
        </thead>
        <tbody>
          {filteredOrders.map((order) => (
            <tr key={order.orderId}>
              <td>{order.orderId}</td>
              <td>{order.orderDate}</td>
              <td>
                {editMode && editOrderId === order.orderId ? (
                  <Form.Control
                    as="select"
                    value={updatedOrder.status.statusId}
                    onChange={(e) =>
                      setUpdatedOrder({
                        ...updatedOrder,
                        status: statuses.find((status) => status.statusId === parseInt(e.target.value)),
                      })
                    }
                  >
                    <option value="">Select Status</option>
                    {statuses.map((status) => (
                      <option key={status.statusId} value={status.statusId}>
                        {status.orderStatus}
                      </option>
                    ))}
                  </Form.Control>
                ) : (
                  order.status.orderStatus
                )}
              </td>
              <td>{order.total}</td>
              <td>{order.shippingMethod}</td>
              <td>
                {order.orderItemsItems.map((item) => (
                  <div key={item.orderItemId}>
                    {item.product.productName} (x{item.quantity})
                  </div>
                ))}
              </td>
              <td>
                  <div>
                    {order.arrivalDetails?.arrivalDate} {order.arrivalDetails?.arrivalTime} <br />
                    {order.arrivalDetails?.country}, {order.arrivalDetails?.city}, {order.arrivalDetails?.streetName} {order.arrivalDetails?.streetNumber}
                  </div>
              </td>
              <td>{order.user.fullName}</td>
              {user && user.role === 'ADMIN' && (
                <td>
                  {editMode && editOrderId === order.orderId ? (
                    <>
                      <Button variant="success" onClick={handleUpdateOrder}>
                        Save
                      </Button>
                      <Button variant="secondary" onClick={handleCancelEdit} className="ml-2">
                        Cancel
                      </Button>
                    </>
                  ) : (
                    <Button variant="primary" onClick={() => handleEditOrder(order.orderId)}>
                      Edit
                    </Button>
                  )}
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

export default OrderList;


