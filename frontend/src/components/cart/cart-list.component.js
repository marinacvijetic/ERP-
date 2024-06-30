import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { loggedUser$ } from "../../state/logged-user.state";
import paymentService from "../../services/payment.service";
import orderArrivalService from "../../services/orderArrival.service";
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import cartService from "../../services/cart.service";
import orderService from "../../services/order.service";
import { Button, Form, Table } from "react-bootstrap";
import CheckoutForm from "../../utils/CheckoutForm";

const CartList = () => {
//CART
    const[cartItems, setCartItems] = useState([]);
    const[totalPrice, setTotalPrice] = useState(0);
    const[clientSecret, setClientSecret] = useState('');
    const[stripe, setStripe] = useState(null);
    const[orderId, setOrderId] = useState(null);
    const [shippingMethod, setShippingMethod] = useState('TAKEAWAY');

    //ARRIVAL DETAILS
const[arrivalDetails, setArrivalDetails] = useState([]);
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
            if(!_user.userId) {
                navigate('/');
            } else if(_user.role !== 'USER') {
                navigate('/')
            }

            setUser(_user);
        });

        return () => {
            subscription.unsubscribe();
        };
    }, [navigate]);

    const options = {
        clientSecret
    };

    const fetchData = async () => {
        try{
            const response = await orderArrivalService.getAll();
            setArrivalDetails(response.data);
        }catch (error) {
            console.error('Error retrieving data:', error);
        }
    };

    const handleAddDetails = async () => {
        try {
            const response = await orderArrivalService.create(newDetails)
            setArrivalDetails([...arrivalDetails, response.data]);
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


    useEffect(() => {
        if(orderId) {
            const stripePromise = loadStripe('pk_test_51JjkL9J39DwJdWKYIFqUiIiVWXVifcaZclToeGaDiSCUI88eGANHLTfIbtqLbQNZqOmaj9312MI1nY2mQf3AcvHy00t2AiJHiG');
            setStripe(stripePromise);

            getClientSecret();
        }
    }, [orderId])

    useEffect(() => {
        fetchCartItems();
    }, []);

    async function getClientSecret() {
        const response = await paymentService.getClientSecret(totalPrice, user.userId, orderId);
        setClientSecret(response.data.clientSecret);
    }

    const fetchCartItems = async () => {
        try{
            const response = cartService.getCart();
            setCartItems(response);
            calculateTotalPrice(response);
        } catch (error) {
            console.error('Failed to fetch cart items:', error);
        }
    };

    const calculateTotalPrice = (items) => {
        let total = 0;
        for(const item of items) {
            total += item.pricePerKilogram * item.quantity;
        }
        setTotalPrice(total);
    };

    const makeOrder = async() => {
        const orderItems = cartItems.map(x => {
            return {
                product: {
                    productId: x.productId,
                    productName: x.productName,
                    pricePerKilogram: x.pricePerKilogram
                },
                quantity: x.quantity,
            }
        })

        const response = await orderService.create({
            orderItems,
            total: totalPrice,
            user: {
                userId: user.userId
            },
            arrivalDetails: {
                arrivalDetailsId: editArrivalDetailsId
            }
        });

        setOrderId(response.data);
        cartService.clearCart();

        alert("Order successfully saved! You can proceed and pay your order online")
    };

    return(
        <div>
            <h4>Shopping Cart</h4>
            <Table striped bordered>
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price per kg</th>
                        <th>Quantity</th>
                        <th>Total price</th>
                    </tr>
                </thead>
                <tbody>
                    {cartItems.map((item) => (
                        <tr key={item.productId}>
                            <td>{item.productName}</td>
                            <td>{item.pricePerKilogram}</td>
                            <td>{item.quantity}</td>
                            <td>{item.pricePerKilogram * item.quantity}</td>
                        </tr>
                    ))}
                    <tr><h4>Total Price: {totalPrice}</h4></tr>
                </tbody>
            </Table>
            <div>
            <h4>Add Order Arrival Details</h4>
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
                        
                            <Button variant="primary" onClick={handleAddDetails} className="mt-2">
                                Add Arrival Details
                            </Button>
                        
                    </Form.Group>
                }
            </div>

            <div>
                {orderId ? 
                    !clientSecret ? 
                        <div>Loading payment data ...</div> :
                            <Elements stripe={stripe} options={options}>
                                <CheckoutForm cartItems={cartItems}/>
                            </Elements>
                :
                    <Button variant="primary" onClick={makeOrder}> 
                     Order 
                    </Button>
                    }
            </div>
        </div>

    );
};

export default CartList;