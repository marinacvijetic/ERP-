class CartService {

    getCart() {
        const cartString = localStorage.getItem('cart');
        let cart = [];
        if (cartString) {
            cart = JSON.parse(cartString);
        }
        
        return cart;
    }

    addToCart(product) {
       const cart = this.getCart();
        const productInCart = cart.find(x => x.productId === product.productId);
        if (productInCart) {
            productInCart.quantity++;
        } else {
            cart.push({
                ...product,
                quantity: 1
            });
        }

        localStorage.setItem('cart', JSON.stringify(cart));
    }

    clearCart() {
        localStorage.removeItem('cart');
    }
}

export default new CartService();