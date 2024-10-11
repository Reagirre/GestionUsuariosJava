package Utils;

import Models.Usuario;
import Models.Departamento;
import Models.Grupo;
import Models.Rol;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CsvReader {
    public static String RUTA_USUARIOS = "C:\\Users\\User\\Desktop\\GestionUsuariosJava\\src\\Data\\usuarios.csv";
    public static String RUTA_GRUPOS = "C:\\Users\\User\\Desktop\\GestionUsuariosJava\\src\\Data\\grupos.csv";
    public static String RUTA_DEPARTAMENTOS = "C:\\Users\\User\\Desktop\\GestionUsuariosJava\\src\\Data\\departamentos.csv";
    public static String RUTA_ROLES = "C:\\Users\\User\\Desktop\\GestionUsuariosJava\\src\\Data\\roles.csv";

    ////////////////////////////LECTURA Y ESCRITURA DE USUARIOS////////////////////////////////////////

    public static List<Usuario> leerUsuarios(String ruta) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");


                if (campos.length < 6) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String email = campos[2];
                    int edad = Integer.parseInt(campos[3]);

                    List<Integer> departamentos = Arrays.stream(campos[4].split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    List<String> grupos = Arrays.stream(campos[5].split(",")) // Asegúrate de que el índice sea 5 para el campo de grupos
                            .map(String::trim)
                            .collect(Collectors.toList());

                    List<String> roles = Arrays.stream(campos[6].split(",")) // Asegúrate de que el índice sea 6 para el campo de roles
                            .map(String::trim)
                            .collect(Collectors.toList());

                    usuarios.add(new Usuario(id, nombre, email, edad, departamentos, grupos, roles));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public static void escribirUsuario(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS, true))) {  // Modo 'true' para agregar al archivo
            String usuarioCsv = convertirUsuarioACsv(usuario);
            bw.write(usuarioCsv);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir el usuario en el archivo CSV: " + e.getMessage());
        }
    }

    private static String convertirUsuarioACsv(Usuario usuario) {
        String departamentos = usuario.getDepartamentos().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        String grupos = usuario.getGrupos().stream()
                .collect(Collectors.joining(","));

        String roles = usuario.getRoles().stream()
                .collect(Collectors.joining(","));

        return usuario.getId() + "," +
                usuario.getNombre() + "," +
                usuario.getEmail() + "," +
                usuario.getEdad() + "," +
                departamentos + "," +
                grupos + "," +
                roles;
    }

    public static void sobreEscribirUsuarios(List<Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS))) {
            bw.write("ID,Nombre,Email,Edad,Departamentos, Roles");
            bw.newLine();

            for (Usuario usuario : usuarios) {
                String usuarioCsv = convertirUsuarioACsv(usuario);
                bw.write(usuarioCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir usuarios en el archivo CSV: " + e.getMessage());
        }
    }


    //////////////////////////////LECTURA Y ESCRITURA DE GRUPOS//////////////////////////////////////
    public static List<Grupo> leerGrupos(String ruta) {
        List<Grupo> grupos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_GRUPOS))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length < 3) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(campos[0]); // Convertir a int
                    String nombre = campos[1];
                    String descripcion = campos[2];

                    grupos.add(new Grupo(id, nombre, descripcion, new ArrayList<>()));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grupos;
    }


    public static void escribirGrupo(Grupo grupo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_GRUPOS, true))) { // Modo 'true' para agregar al archivo
            String grupoCsv = convertirGrupoACsv(grupo);
            bw.write(grupoCsv);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir el grupo en el archivo CSV: " + e.getMessage());
        }
    }

    public static void sobreEscribirGrupos(List<Grupo> grupos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_GRUPOS))) {
            bw.write("ID,Nombre,Descripción");
            bw.newLine();

            for (Grupo grupo : grupos) {
                String grupoCsv = convertirGrupoACsv(grupo);
                bw.write(grupoCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir grupos en el archivo CSV: " + e.getMessage());
        }
    }

    private static String convertirGrupoACsv(Grupo grupo) {
        return grupo.getId() + "," + grupo.getNombre() + "," + grupo.getDescripcion();
    }

    /////////////////////////////LECTURA Y ESCRITURA DE DEPARTAMENTOS/////////////////////////////////

    public static List<Departamento> leerDepartamentos(String ruta) {
        List<Departamento> departamentos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_DEPARTAMENTOS))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length < 3) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String descripcion = campos[2];

                    List<String> usuarios = new ArrayList<>();

                    departamentos.add(new Departamento(id, nombre, descripcion, usuarios));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return departamentos;
    }

    public static void escribirDepartamento(Departamento departamento) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DEPARTAMENTOS, true))) {
            String linea = departamento.getId() + "," + departamento.getNombre() + "," + departamento.getDescripcion() + "," + String.join(";", departamento.getUsuarios());
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    public static void sobrescribirDepartamentos(List<Departamento> departamentos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DEPARTAMENTOS))) {
            bw.write("ID,Nombre,Descripción,Usuarios");
            bw.newLine();

            for (Departamento departamento : departamentos) {
                String linea = departamento.getId() + "," +
                        departamento.getNombre() + "," +
                        departamento.getDescripcion() + "," +
                        String.join(",", departamento.getUsuarios());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir departamentos en el archivo CSV: " + e.getMessage());
        }
    }

    ///////////////////////////////////LECTURA Y ESCRITURA DE ROLES////////////////////////////////
    public static List<Rol> leerRoles(String ruta) {
        List<Rol> roles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ROLES))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length < 3) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];

                    List<String> permisos = Arrays.asList(campos[2].replace("\"", "").split(";"));

                    roles.add(new Rol(id, nombre, permisos));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public static void escribirRol(Rol rol) {
        List<Rol> rolesExistentes = leerRoles("Data/roles.csv");
        if (rolesExistentes.stream().anyMatch(r -> r.getId() == rol.getId())) {
            System.out.println("El rol con ID " + rol.getId() + " ya existe. No se puede agregar.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Data/roles.csv", true))) {
            String rolCsv = convertirRolACsv(rol);
            bw.write(rolCsv);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir el rol en el archivo CSV: " + e.getMessage());
        }
    }

    private static String convertirRolACsv(Rol rol) {
        String permisos = String.join(";", rol.getPermisos()); // Convertir la lista de permisos a una cadena
        return rol.getId() + "," + rol.getNombre() + ",\"" + permisos + "\""; // Se agrega comillas para permitir permisos con comas
    }

    public static void sobrescribirRoles(List<Rol> roles) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ROLES))) {
            bw.write("ID,Nombre,Permisos");
            bw.newLine();

            for (Rol rol : roles) {
                String rolCsv = convertirRolACsv(rol);
                bw.write(rolCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al sobrescribir roles en el archivo CSV: " + e.getMessage());
        }
    }
}
