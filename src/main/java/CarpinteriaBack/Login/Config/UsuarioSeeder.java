package CarpinteriaBack.Login.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import CarpinteriaBack.Empleado.Model.Empleado;
import CarpinteriaBack.Empleado.Repository.EmpleadoRepository;
import CarpinteriaBack.Login.Model.Rol;
import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Respository.RolRepository;
import CarpinteriaBack.Login.Service.UsuarioService;

@Component
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final EmpleadoRepository empleadoRepository;

    public UsuarioSeeder(UsuarioService usuarioService,
                         PasswordEncoder passwordEncoder,
                         RolRepository rolRepository,
                         EmpleadoRepository empleadoRepository) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) {
        String username = "trabajador1";
        String password = "trab123";

        // Usar servicio en lugar de repository directo
        if (usuarioService.findByUsername(username).isEmpty()) {

            // Buscar empleado por DNI, si no existe lo crea automáticamente
            Empleado emp = empleadoRepository.findByDni("87654321")
                .orElseGet(() -> {
                    Rol rolTrabajador = rolRepository.findByNombre("TRABAJADOR");
                    Empleado nuevoEmp = new Empleado();
                    nuevoEmp.setNombre("Juan");
                    nuevoEmp.setApellidos("Perez");
                    nuevoEmp.setDni("87654321");
                    nuevoEmp.setCorreo("juan.perez@correo.com");
                    nuevoEmp.setCodigoTrabajador("TRB001");
                    nuevoEmp.setRol(rolTrabajador);
                    // Configura otros campos necesarios para Empleado si es obligatorio
                    nuevoEmp.setFechaNacimiento(java.time.LocalDate.of(1990, 1, 1));
                    nuevoEmp.setFechaContratacion(java.time.LocalDate.now());
                    nuevoEmp.setDireccion("Av. Principal 123");
                    nuevoEmp.setGenero("Masculino");
                    nuevoEmp.setCelular("999888777");
                    return empleadoRepository.save(nuevoEmp);
                });

            // Obtener rol trabajador
            Rol rol = rolRepository.findByNombre("TRABAJADOR");

            // Crear usuario con contraseña encriptada
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setPassword(passwordEncoder.encode(password));
            u.setEmpleado(emp);
            emp.setRol(rol);

            usuarioService.saveUsuario(u);

            System.out.println("Usuario creado: " + username + " con rol " + rol.getNombre());
        }
    }
}