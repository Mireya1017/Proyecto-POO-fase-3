public boolean autenticarse(String correo, String contraseña) {
        return this.correo.equalsIgnoreCase(correo) && this.contraseña.equals(contraseña);
    }