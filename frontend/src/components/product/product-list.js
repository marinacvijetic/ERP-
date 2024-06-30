import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loggedUser$ } from '../../state/logged-user.state';
import productService from '../../services/product.service';
import categoryService from '../../services/category.service';
import cartService from '../../services/cart.service';
import { Button, Container, Form, Table } from 'react-bootstrap';

const ProductList = () => {
   const[products, setProducts] = useState([]);
   const[filteredProducts, setFilteredProducts] = useState([]);
   const[searchTerm, setSearchTerm] = useState('');
   const[sortColumn, setSortColumn] = useState('');
   const[sortOrder, setSortOrder] = useState('asc');
   const[newProduct, setNewProduct] = useState({
    productId: '',
    productName: '', 
    description: '',
    pricePerKilogram: '',
    category: {
        categoryId: '',
        categoryName: ''
    }

   });

   const[editMode, setEditMode] = useState(false);
   const[editProductId, setEditProductId] = useState('');
   const[category, setCategory] = useState([]);

   const[user, setUser] = useState(null);

   const navigate = useNavigate();
   useEffect(() =>{
    const subscription = loggedUser$.subscribe(_user => {
        setUser(_user);
    });

    return () =>{
        subscription.unsubscribe();
    };

   }, []);

   useEffect(() => {
    fetchData();
    fetchCategory();
   }, []);

   const fetchData = async () => {
    try{
        const response = await productService.getAll();
        setProducts(response.data);
        setFilteredProducts(response.data);
    } catch (error) {
        console.error('Error retrieving data:', error);
    }

   };

   const fetchCategory = async () => {
    try{
        const response = await categoryService.getAll();
        setCategory(response.data);
    }catch(error) {
        console.error('Error retrieving categories:', error);
    }
   };

   const handleSearch = (e) => {
    const searchTerm = e.target.value;
    setSearchTerm(searchTerm);
    filterProducts(searchTerm);
   };

   const filterProducts = (searchTerm) => {
    const filtered = products.filter(
        (product) =>
            product.productName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            product.category.categoryName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            product.description.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredProducts(filtered);
   };

   const handleSort = (column) => {
    if(column === sortColumn) {
        setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    }else{
        setSortColumn(column);
        setSortOrder('asc');
    }
    const sortedProducts = [...filterProducts].sort((a,b) => {
        const valueA = column === 'category' ? a[column].categoryName : a[column];
        const valueB = column === 'category' ? b[column].categoryName : b[column];

        if(valueA < valueB) {
            return sortOrder === 'asc' ? -1 : 1;
        }
        if(valueA > valueB) {
            return sortOrder === 'asc' ? 1 : -1;
        }

        return 0;
    });

    setFilteredProducts(sortedProducts);
   };

   const handleAddProduct = async () => {
    try{
        const response = await productService.create(newProduct);
        const newProductData = response.data;
        setProducts([...products, newProductData]);
        setNewProduct({
            productId: '',
            productName: '',
            description: '',
            pricePerKilogram: '',
            category: {
                categoryId: '',
                categoryName: ''
            }

        });
    }catch (error) {
        console.error('Error adding product:', error);
    }
   };

   const handleEditProduct = (productId) => {
    setEditMode(true);
    setEditProductId(productId);
    const productToUpdate = products.find((product) => product.productId === productId);
    setNewProduct(productToUpdate);
   };

   const handleUpdateProduct = async () => {
    try{
        const response = await productService.update(editProductId, newProduct);
        const updatedProductData = response.data;
        const updatedProducts = products.map((product) => (product.productId === updatedProductData.productId ? updatedProductData : product));
        setProducts(updatedProducts);
        setNewProduct({
            productId: '',
            productName: '',
            description: '',
            pricePerKilogram: '',
            category: {
                categoryId: '',
                categoryName: ''
            }
        });
        setEditProductId('');
        setEditMode(false);
    } catch (error) {
        console.error('Error updating the product:', error);
    }
   };

   const handleDeleteProduct = async (productId) => {
    try{
        await productService.delete(productId)
        const updatedProducts = products.filter((product) => product.productId !== productId);
        setProducts(updatedProducts);
        setFilteredProducts(updatedProducts);
    } catch (error) {
        console.error('Error deleting the product:', error);
    }
   };

   const handleAddToCart = async (productId) => {
    try{
        const product = products.find((product) => product.productId === productId);
        cartService.addToCart(product);
        alert('Product added ro cart successfully!');
    }catch(error) {
        console.error('Failed to add the product to cart:', error);
    }
   };

   const handleCancelEdit = () => {
    setNewProduct({
        productId: '',
        productName: '',
        description: '',
        pricePerKilogram: '',
        category: {
            categoryId: '',
            categoryName: ''
        }
    });
    setEditProductId('');
    setEditMode(false);
   };
   
   
    return (
        <Container>
            <Form.Group>
                <Form.Control
                type='text'
                placeholder='Search Products'
                value={searchTerm}
                onChange={handleSearch}
                />
            </Form.Group>

            <Table striped bordered>
                <thead>
                    <tr>
                        <th onClick={() => handleSort('productId')}>ID</th>
                        <th onClick={() => handleSort('productName')}>Product Name</th>
                        <th onClick={() => handleSort('description')}>Description</th>
                        <th onClick={() => handleSort('pricePerKilogram')}>Price Per kg</th>                    
                        <th onClick={() => handleSort('category')}>Category</th>
                        {user && user.userId && <th>Actions</th>}
                    </tr>
                </thead>
                <tbody>
                    {filteredProducts.map((product) => (
                        <tr key={product.productId}>
                            <td>{product.productId}</td>
                            <td>{product.productName}</td>
                            <td>{product.description}</td>
                            <td>{product.pricePerKilogram}</td>
                            <td>{product.category.categoryName}</td>
                            {user && user.userId && <td>
                                {user.role === 'ADMIN' && <Button
                                variant='outline-primary'
                                size='sm'
                                className='mr-2'
                                onClick={() => handleEditProduct(product.productId)}
                            > 
                            Edit
                            </Button>}
                            {user.role === 'ADMIN' && <Button
                            variant='outline-danger'
                            size='sm'
                            onClick={() => handleDeleteProduct(product.productId)}
                            >
                            Delete
                            </Button>}
                            {user.role === 'USER' && <Button variant="primary" onClick={() => handleAddToCart(product.productId)}>
                            Add to Cart
                            </Button>
                            }
                        </td>
                    }
                    </tr>
                    ))}
                </tbody>
            </Table>
    {user && user.role === 'ADMIN' && <Form.Group>
        <Form.Control
          type="text"
          placeholder="Enter product name"
          value={newProduct.productName}
          onChange={(e) => setNewProduct({ ...newProduct, productName: e.target.value })}
        />
        <Form.Control
          type="text"
          placeholder="Enter description"
          value={newProduct.description}
          onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })}
        />
        <Form.Control
          type="number"
          placeholder="Enter price"
          value={newProduct.pricePerKilogram}
          onChange={(e) => setNewProduct({ ...newProduct, pricePerKilogram: e.target.value })}
        />
        <Form.Control as="select" value={newProduct.category.categoryId} onChange={(e) => setNewProduct({ ...newProduct, category: { ...newProduct.category, categoryId: e.target.value } })}>
          <option value="">Select Category</option>
          {category.map((category) => (
            <option key={category.categoryId} value={category.categoryId}>
              {category.categoryName}
            </option>
          ))}
        </Form.Control>
        {editMode ? (
          <>
            <Button variant="success" onClick={handleUpdateProduct}>
              Update Product
            </Button>
            <Button variant="secondary" onClick={handleCancelEdit} className="ml-2">
              Cancel
            </Button>
          </>
            ) : (
          <Button variant="primary" onClick={handleAddProduct} className="mt-2">
            Add Product
          </Button>
            )}
      </Form.Group>
    }
        </Container>
    );
};

export default ProductList;