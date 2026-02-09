package CarpinteriaBack.Empleado.Service;

import CarpinteriaBack.Empleado.Model.Empleado;
import CarpinteriaBack.Login.Model.Rol;
import CarpinteriaBack.Empleado.Repository.EmpleadoRepository;
import CarpinteriaBack.Login.Respository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           RolRepository rolRepository) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
    }

    // Registrar empleado con validaciones
    public Empleado registrarEmpleado(Empleado empleado) {

        // Validar rol
        if (empleado.getRol() == null || empleado.getRol().getIdRol() == null) {
            throw new RuntimeException("El rol del empleado es obligatorio.");
        }

        Rol rol = rolRepository.findById(empleado.getRol().getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado."));

        empleado.setRol(rol);

        // Validar duplicados
        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new RuntimeException("Ya existe un empleado con el DNI: " + empleado.getDni());
        }

        if (empleadoRepository.existsByCorreo(empleado.getCorreo())) {
            throw new RuntimeException("Ya existe un empleado con el correo: " + empleado.getCorreo());
        }

        if (empleadoRepository.existsByCodigoTrabajador(empleado.getCodigoTrabajador())) {
            throw new RuntimeException("Ya existe un empleado con el código de trabajador: " + empleado.getCodigoTrabajador());
        }

        return empleadoRepository.save(empleado);
    }

    // Listar todos los empleados
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    // Obtener empleado por ID
    public Empleado obtenerPorId(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }

    // Actualizar empleado
    public Empleado actualizarEmpleado(Integer id, Empleado datos) {

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        // Validar cambios en DNI
        if (!empleado.getDni().equals(datos.getDni()) &&
                empleadoRepository.existsByDni(datos.getDni())) {
            throw new RuntimeException("Ya existe otro empleado con el DNI: " + datos.getDni());
        }

        // Validar cambios en correo
        if (!empleado.getCorreo().equals(datos.getCorreo()) &&
                empleadoRepository.existsByCorreo(datos.getCorreo())) {
            throw new RuntimeException("Ya existe otro empleado con el correo: " + datos.getCorreo());
        }

        // Validar cambios en código de trabajador
        if (!empleado.getCodigoTrabajador().equals(datos.getCodigoTrabajador()) &&
                empleadoRepository.existsByCodigoTrabajador(datos.getCodigoTrabajador())) {
            throw new RuntimeException("Ya existe otro empleado con el código de trabajador: " + datos.getCodigoTrabajador());
        }

        // Actualizar rol si viene
        if (datos.getRol() != null && datos.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findById(datos.getRol().getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado."));
            empleado.setRol(rol);
        }

        // Actualizar datos básicos
        empleado.setNombre(datos.getNombre());
        empleado.setApellidos(datos.getApellidos());
        empleado.setDni(datos.getDni());
        empleado.setCorreo(datos.getCorreo());
        empleado.setCodigoTrabajador(datos.getCodigoTrabajador());
        empleado.setFechaNacimiento(datos.getFechaNacimiento());
        empleado.setFechaContratacion(datos.getFechaContratacion());
        empleado.setDireccion(datos.getDireccion());
        empleado.setGenero(datos.getGenero());
        empleado.setCelular(datos.getCelular());

        return empleadoRepository.save(empleado);
    }

    // Eliminar empleado
    public void eliminarEmpleado(Integer id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        empleadoRepository.delete(empleado);
    }


    // 🔍 NUEVO MÉTODO DE BÚSQUEDA
    public List<Empleado> buscar(String texto, Integer idRol) {

        boolean textoVacio = (texto == null || texto.trim().isEmpty());

        if (textoVacio && idRol == null) {
            return empleadoRepository.findAll();
        }

        return empleadoRepository.buscarPorTextoYRol(
                texto == null ? "" : texto,
                idRol
        );
    }
}
