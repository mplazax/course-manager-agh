import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../../utils/apiClient'; // Import apiClient
import { AuthContext } from '../../context/AuthContext';
import './RegisterPage.css';

const RegisterPage = () => {
    const [formData, setFormData] = useState({
        firstname: '',
        surname: '',
        age: '',
        email: '',
        password: '',
        isOrganizer: false,
    });
    const navigate = useNavigate();
    const { login } = useContext(AuthContext);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === 'checkbox' ? checked : value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Rejestracja użytkownika
            await apiClient.post('/api/auth/register', formData);

            // Automatyczne logowanie po rejestracji
            const loginResponse = await apiClient.post('/api/auth/login', {
                email: formData.email,
                password: formData.password,
            });

            const userData = loginResponse.data;

            // Zapisanie danych użytkownika w kontekście
            login({
                id: userData.userId,
                firstname: userData.firstname,
                surname: userData.surname,
                email: userData.email,
                token: userData.token,
            });

            alert('Rejestracja i logowanie zakończone sukcesem!');
            navigate('/'); // Przekierowanie na stronę główną
        } catch (error) {
            console.error('Error during registration or login:', error);

            const errorMessage =
                error.response?.data || 'Wystąpił błąd. Spróbuj ponownie.';
            alert(`Błąd: ${errorMessage}`);
        }
    };

    return (
        <div className="login-subsite">
            <div className="login-container">
                <h1>Register</h1>
                <form className="form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="firstname">First Name</label>
                        <input
                            type="text"
                            id="firstname"
                            name="firstname"
                            placeholder="Enter your first name"
                            value={formData.firstname}
                            onChange={handleChange}
                            maxLength={50}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="surname">Last Name</label>
                        <input
                            type="text"
                            id="surname"
                            name="surname"
                            placeholder="Enter your last name"
                            value={formData.surname}
                            onChange={handleChange}
                            maxLength={50}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="age">Age</label>
                        <input
                            type="number"
                            id="age"
                            name="age"
                            placeholder="Enter your age"
                            value={formData.age}
                            onChange={handleChange}
                            min={0}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Enter your email"
                            value={formData.email}
                            onChange={handleChange}
                            maxLength={100}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Enter your password"
                            value={formData.password}
                            onChange={handleChange}
                            minLength={8}
                            maxLength={255}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="isOrganizer">
                            <input
                                type="checkbox"
                                id="isOrganizer"
                                name="isOrganizer"
                                checked={formData.isOrganizer}
                                onChange={handleChange}
                            />
                            {' '}Organizer
                        </label>
                    </div>
                    <button type="submit" className="btn-submit">
                        Register
                    </button>
                </form>
                <p>
                    Already have an account?{' '}
                    <button
                        className="btn-toggle"
                        onClick={() => navigate('/login')}
                    >
                        Login here
                    </button>
                </p>
            </div>
        </div>
    );
};

export default RegisterPage;
