import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import paymentService from "../../services/payment.service";
import { FormControl, Table } from "react-bootstrap";
import {loggedUser$} from "../../state/logged-user.state";

const PaymentList = () => {
    const[payments, setPayments] = useState([]);
    const[filteredPayments, setFilteredPayments] = useState([]);
    const[sortField, setSortField] = useState('');
    const[sortOrder, setSortOrder] = useState('asc');
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(5);

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
    }, [navigate]);

    useEffect(() => {
        fetchPayments();
      }, []);
    
    
  const fetchPayments = async () => {
    try {
      const response = await paymentService.getAll();
      setPayments(response.data);
      setFilteredPayments(response.data);
    } catch (error) {
      console.error('Failed to fetch payments:', error);
    }
  };

  const handleSort = (field) => {
    if (field === sortField) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortOrder('asc');
    }
  };

  const handleSearch = (e) => {
    const searchTerm = e.target.value.toLowerCase();
    setSearchTerm(searchTerm);

    const filteredPayments = payments.filter((payment) => {
      return (
        payment.order.user.firstname.toLowerCase().includes(searchTerm) ||
        payment.order.user.lastname.toLowerCase().includes(searchTerm)
      );
    });

    setFilteredPayments(filteredPayments);
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentPayments = filteredPayments.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredPayments.length / itemsPerPage);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div>
        <div className="mb-3">
        <FormControl
        type="text"
        placeholder="Search by firstname or lastname"
        value={searchTerm}
        onChange={handleSearch}
        />
        </div>
        <Table>
            <thead>
                <tr>
                    <th onClick={() => handleSort('paymentId')}>Payment ID</th>
                    <th onClick={() => handleSort('amount')}>Amount</th>
                    <th onClick={() => handleSort('paymentDate')}>Payment Date</th>
                    <th onClick={() => handleSort('paymentMethod')}>Payment Method</th>
                    <th onClick={() => handleSort('order.user.firstname')}>User firstname</th>
                    <th onClick={() => handleSort('order.user.lastname')}>User lastname</th>
                </tr>
            </thead>
            <tbody>
                {currentPayments.map((payment) => (
                    <tr key={payment.paymentId}>
                        <td>{payment.paymentId}</td>
                        <td>{payment.amount}</td>
                        <td>{payment.paymentDate}</td>
                        <td>{payment.paymentMethod}</td>
                        <td>{payment.order.user.firstname}</td>
                        <td>{payment.order.user.lastname}</td>
                    </tr>
                ))}
            </tbody>
        </Table>
        {/* Pagination */}
        <nav>
            <ul className="pagination">
            {Array.from({ length: totalPages }, (_, index) => (
            <li key={index} className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}>
              <button className="page-link" onClick={() => paginate(index + 1)}>
                {index + 1}
              </button>
            </li>
            ))}
            </ul>
        </nav>
    </div>
  )

};


export default PaymentList;