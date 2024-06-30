import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { loggedUser$ } from "../../state/logged-user.state";
import orderArrivalService from "../../services/orderArrival.service";
import { Button, Container, Form, Table } from "react-bootstrap";

const OrderArrivalDetailsList = () => {

    const[arrivalDetails, setArrivalDetails] = useState([]);
    const[filteredDetails, setFilteredDetails] = useState([]);
    const[searchTerm, setSearchTerm] = useState('');
    const[sortColumn, setSortColumn] = useState('')
    const[sortOrder, setSortOrder] = useState('asc');
    const[newDetails, setNewDetails] = useState({
        arrivalDetailsId: '',
        arrivalDate: '',
        arrivalTime: '',
        country: '',
        postalCode: '',
        city: '',
        streetName: '',
        streetNumber: '',
    });

    const[editMode, setEditMode] = useState(false);
    const[editArrivalDetailsId, setEditArrivalDetailsId] = useState('');

    const[user, setUser] = useState(null);

    const navigate = useNavigate();
    useEffect(() => {
        const subscription = loggedUser$.subscribe(_user => {
            setUser(_user);
        });

        return () => {
            subscription.unsubscribe();
        }
    }, [navigate]);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try{
            const response = await orderArrivalService.getAll();
            setArrivalDetails(response.data);
            setFilteredDetails(response.data);
        }catch (error) {
            console.error('Error retrieving data:', error);
        }
    };

    const handleSearch = (e) => {
        const searchTerm = e.target.value;
        setSearchTerm(searchTerm);
        filterArrivalDetails(searchTerm);
    }

    const filterArrivalDetails = (searchTerm) =>{
        const filtered = arrivalDetails.filter(
            (arrivalDetail) =>
                arrivalDetail.city.toLowerCase().includes(searchTerm.toLowerCase()) ||
                arrivalDetail.streetName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                arrivalDetail.country.toLowerCase().includes(searchTerm.toLowerCase())
        );

        setFilteredDetails(filtered);
    };

    const handleSort = (column) => {
        if(column === sortColumn) {
            setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
        }else {
            setSortColumn(column);
            setSortOrder('asc');
        }

        const sortedDetails = [...filteredDetails].sort((a,b) => {
            const valueA = column === 'city' ? a[column].city : a[column];
            const valueB = column === 'city' ? b[column].city : b[column];

            if(valueA<valueB) {
                return sortOrder === 'asc' ? -1 : 1;
            }
            if(valueA > valueB) {
                return sortOrder === 'asc' ? 1 : -1;
            }
            return 0;
        });

        setFilteredDetails(sortedDetails);
    };

    const handleAddDetails = async () => {
        try {
            const response = await orderArrivalService.create(newDetails)
            const newDetailsData = response.data;
            setArrivalDetails([...arrivalDetails, newDetailsData]);
            setFilteredDetails([...arrivalDetails, newDetailsData]);
            setNewDetails({
                arrivalDetailsId: '',
                arrivalDate: '',
                arrivalTime: '',
                country: '',
                postalCode: '',
                city: '',
                streetName: '',
                streetNumber: '',
            });
        } catch(error) {
            console.error('Error adding arrival details: ', error);
        }
    };

    const handleEditArrivalDetails = (arrivalDetailsId) => {
        setEditMode(true);
        setEditArrivalDetailsId(arrivalDetailsId);
        const detailsToUpdate = arrivalDetails.find((details) => details.arrivalDetailsId === arrivalDetailsId);
        setNewDetails(detailsToUpdate);
    };

    const handleUpdateArrivalDetails = async () => {
        try {
            const response = await orderArrivalService.update(editArrivalDetailsId, newDetails)
            const updatedDetailsData = response.data;
            const updatedDetails = arrivalDetails.map((details) => (details.arrivalDetailsId === updatedDetails.arrivalDetailsId ? updatedDetailsData : details));
            setArrivalDetails(updatedDetails);
            setFilteredDetails(updatedDetails);
            setNewDetails({
                arrivalDetailsId: '',
                arrivalDate: '',
                arrivalTime: '',
                country: '',
                postalCode: '',
                city: '',
                streetName: '',
                streetNumber: '',
            });
            setEditArrivalDetailsId('');
            setEditMode(false);
        }catch(error) {
            console.error('Error updating arrival details:', error);
        }
    };

    const handleDeleteArrivalDetails = async (arrivalDetailsId) => {
        try {
            await orderArrivalService.delete(arrivalDetailsId)
            const updatedDetails = arrivalDetails.filter((details) => details.arrivalDetailsId !== arrivalDetailsId);
            setArrivalDetails(updatedDetails);
            setFilteredDetails(updatedDetails);
        }catch(error) {
            console.error('Error deleting arrival details:', error);
        }
    };

    const handleCancelEdit = () => {
        setNewDetails({
            arrivalDetailsId: '',
            arrivalDate: '',
            arrivalTime: '',
            country: '',
            postalCode: '',
            city: '',
            streetName: '',
            streetNumber: '',
        });
        setEditArrivalDetailsId('');
        setEditMode(false);
    };

    return (
        <Container>
            <Form.Group>
                <Form.Control
                    type="text"
                    placeholder="Search Order Arrival Details"
                    value={searchTerm}
                    onChange={handleSearch}
                />
            </Form.Group>

            <Table striped bordered>
                <thead>
                    <tr>
                        <th onClick={() => handleSort('arrivalDetailsId')}>ID</th>
                        <th onClick={() => handleSort('arrivalDate')}>Arrival Date</th>
                        <th onClick={() => handleSort('arrivalTime')}>Arrival Time</th>
                        <th onClick={() => handleSort('country')}>Country</th>
                        <th onClick={() => handleSort('postalCode')}>Postal Code</th>
                        <th onClick={() => handleSort('city')}>City</th>
                        <th onClick={() => handleSort('streetName')}>Street</th>
                        <th onClick={() => handleSort('streetNumber')}>Street No.</th>
                        {user && user.userId && <th>Actions</th>}
                    </tr>
                </thead>
                    <tbody>
                        {filteredDetails.map((details) => (
                            <tr key={details.arrivalDetailsId}>
                                <td>{details.arrivalDetailsId}</td>
                                <td>{details.arrivalDate}</td>
                                <td>{details.arrivalTime}</td>
                                <td>{details.country}</td>
                                <td>{details.postalCode}</td>
                                <td>{details.city}</td>
                                <td>{details.streetName}</td>
                                <td>{details.streetNumber}</td>
                                {user && user.userId && <td>
                                    <Button 
                                    variant="outline-primary"
                                    size="sm"
                                    className="mr-2"
                                    onClick={() => handleEditArrivalDetails(details.arrivalDetailsId)}>
                                        Edit
                                    </Button>
                                    <Button
                                    variant="outline-danger"
                                    size="sm"
                                    onClick={() => handleDeleteArrivalDetails(details.arrivalDetailsId)}>
                                        Delete
                                    </Button>
                                </td>
                                }
                            </tr>
                        ))}
                    </tbody>
                </Table>
                    {user && user.userId && <Form.Group>
                        <Form.Control
                        type="date"
                        value={newDetails.arrivalDate}
                        onChange={(e) => setNewDetails({...newDetails, arrivalDate: e.target.value})}
                        />    
                        <Form.Control
                        type="time"
                        value={newDetails.arrivalTime}
                        onChange={(e) => setNewDetails({...newDetails, arrivalTime: e.target.value})}
                        /> 
                        <Form.Control
                        type="text"
                        placeholder="Enter country"
                        value={newDetails.country}
                        onChange={(e) => setNewDetails({...newDetails, country: e.target.value})}
                        /> 
                        <Form.Control
                        type="text"
                        placeholder="Enter postal code"
                        value={newDetails.postalCode}
                        onChange={(e) => setNewDetails({...newDetails, postalCode: e.target.value})}
                        />  
                        <Form.Control
                        type="text"
                        placeholder="Enter city"
                        value={newDetails.city}
                        onChange={(e) => setNewDetails({...newDetails, city: e.target.value})}
                        /> 
                        <Form.Control
                        type="text"
                        placeholder="Enter street name"
                        value={newDetails.streetName}
                        onChange={(e) => setNewDetails({...newDetails, streetName: e.target.value})}
                        /> 
                                                <Form.Control
                        type="text"
                        placeholder="Enter street number"
                        value={newDetails.streetNumber}
                        onChange={(e) => setNewDetails({...newDetails, streetNumber: e.target.value})}
                        /> 
                        {editMode ? (
                            <>
                            <Button variant="success" onClick={handleUpdateArrivalDetails}>
                                Update Arrival Details
                            </Button>
                            <Button variant="secondary" onClick={handleCancelEdit} className="ml-2">
                                Cancel
                            </Button>
                            </>
                        ) : (
                            <Button variant="primary" onClick={handleAddDetails} className="mt-2">
                                Add Arrival Details
                            </Button>
                        )}
                    </Form.Group>
                }
        </Container>
    );
};


export default OrderArrivalDetailsList;