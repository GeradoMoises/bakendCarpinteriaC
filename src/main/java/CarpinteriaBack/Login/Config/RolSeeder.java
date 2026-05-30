package CarpinteriaBack.Login.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import CarpinteriaBack.Login.Model.Rol;
import CarpinteriaBack.Login.Respository.RolRepository;

@Component
public class RolSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;

    public RolSeeder(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) {
        String[] roles = {"ADMINISTRADOR", "TRABAJADOR"};
        for (String r : roles) {
            if (!rolRepository.existsByNombre(r)) {
                Rol rol = new Rol();
                rol.setNombre(r);
                rolRepository.save(rol);
                System.out.println("Rol creado: " + r);
            }
        }
    }
}
