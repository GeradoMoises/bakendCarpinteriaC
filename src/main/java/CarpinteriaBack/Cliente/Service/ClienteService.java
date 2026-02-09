package CarpinteriaBack.Cliente.Service;

import CarpinteriaBack.Cliente.Model.Cliente;
import CarpinteriaBack.Cliente.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Registrar cliente (validación de DNI)
    public Cliente registrarCliente(Cliente cliente) {

        if (clienteRepository.existsByDni(cliente.getDni())) {
            throw new RuntimeException("El DNI ya está registrado.");
        }

        return clienteRepository.save(cliente);
    }

    // Buscar por DNI
    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + dni));
    }

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Actualizar cliente por DNI
    public Cliente actualizarPorDni(String dni, Cliente datos) {

        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + dni));

        // Si cambia el DNI → verificar duplicado
        if (!cliente.getDni().equals(datos.getDni()) &&
                clienteRepository.existsByDni(datos.getDni())) {
            throw new RuntimeException("El DNI ya está registrado por otro cliente.");
        }

        cliente.setNombre(datos.getNombre());
        cliente.setApellidos(datos.getApellidos());
        cliente.setCelular(datos.getCelular());
        cliente.setCorreo(datos.getCorreo());
        cliente.setDni(datos.getDni());

        return clienteRepository.save(cliente);
    }

    // Eliminar cliente por DNI
    public void eliminarPorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + dni));

        clienteRepository.delete(cliente);
    }
}
