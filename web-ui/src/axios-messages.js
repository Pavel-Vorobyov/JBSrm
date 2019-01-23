import axios from 'axios';

const instance = axios.create({
    // baseURL: window.location.origin,
    baseURL: 'http://localhost:8080',
    timeout: 10000,
});

(function() {
    let token = localStorage.getItem('token');
    let toketnType = localStorage.getItem('tokenType');
    if (token) {
        axios.defaults.headers.common['Authorization'] = toketnType + ' ' + token;
    } else {
        delete axios.defaults.headers.common['Authorization'];
    }
})();

export default instance;