import React, { useState, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import apiClient from "../../utils/apiClient";

function EditPassword({ user, onCancel }) {
    const { logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        currentPassword: "",
        newPassword: "",
        repeatNewPassword: "",
    });

    const [isSaving, setIsSaving] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSaving(true);

        try {
            // Frontend validation
            if (formData.newPassword.length < 8) {
                alert("Nowe hasło musi zawierać co najmniej 8 znaków.");
                return;
            }
            if (formData.newPassword !== formData.repeatNewPassword) {
                alert("Nowe hasła nie są identyczne.");
                return;
            }

            // Sending request to update password
            const response = await apiClient.put(`/api/users/${user.id}/password`, {
                currentPassword: formData.currentPassword,
                newPassword: formData.newPassword,
            });

            if (response.status === 200) {
                alert("Hasło zostało zmienione. Zaloguj się ponownie.");
                logout();
                navigate("/login");
            } else {
                alert("Nie udało się zaktualizować hasła.");
            }
        } catch (error) {
            console.error("Błąd podczas zmiany hasła:", error);
            alert("Wystąpił błąd. Sprawdź aktualne hasło i spróbuj ponownie.");
        } finally {
            setIsSaving(false);
        }
    };

    return (
        <div>
            <h2>Change Password</h2>
            <p>Po zmianie hasła należy się ponownie zalogować.</p>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Aktualne hasło:</label>
                    <input
                        type="password"
                        name="currentPassword"
                        value={formData.currentPassword}
                        onChange={handleChange}
                        required
                        minLength={8}
                    />
                </div>
                <div>
                    <label>Nowe hasło (min. 8 znaków):</label>
                    <input
                        type="password"
                        name="newPassword"
                        value={formData.newPassword}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Powtórz nowe hasło:</label>
                    <input
                        type="password"
                        name="repeatNewPassword"
                        value={formData.repeatNewPassword}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div style={{ marginTop: "1rem" }}>
                    <button type="submit" disabled={isSaving}>
                        {isSaving ? "Saving..." : "Change Password"}
                    </button>
                    <button type="button" onClick={onCancel}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}

export default EditPassword;
