package co.edu.unbosque.model.dto;

public class ClienteDTO {

    private int id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String nombreCompleto; // Se almacena como atributo para ser modificable en JSF

    public ClienteDTO() {
    }

    public ClienteDTO(int id, String cedula, String nombres, String apellidos) {
        this.id = id;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreCompleto = nombres + " " + apellidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
        actualizarNombreCompleto();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
        actualizarNombreCompleto();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        // Opcionalmente podríamos dividirlo en nombres y apellidos si es necesario
        String[] partes = nombreCompleto.split(" ", 2);
        if (partes.length == 2) {
            this.nombres = partes[0];
            this.apellidos = partes[1];
        } else {
            this.nombres = nombreCompleto;
            this.apellidos = "";
        }
    }

    private void actualizarNombreCompleto() {
        this.nombreCompleto = nombres + " " + apellidos;
    }
}
