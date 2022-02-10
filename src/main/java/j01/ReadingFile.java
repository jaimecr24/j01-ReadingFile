package j01;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ReadingFile {

  public static void main(String[] args) {
    //
      Charset charset = StandardCharsets.UTF_8;
      Path path = Paths.get("datos.txt");
      final int ageLimit = 25;
      final String separator = ":";

      try {
          // Get lines from file as Stream of Strings.
          Stream<String> lines = Files.lines(path,charset);

          // Map lines to a List of Persona
          List<Persona> personList = lines.map(e -> new Persona(e,separator)).toList();
          // It's possible to do without this line and use directly:
          // lines.map(Persona::new).filter(...).map(...).forEach(...);

          personList.stream()
                  .filter(e -> e.isMinorOf(ageLimit))
                  .map(Persona::toString)
                  .forEach(System.out::println);
      }
      catch (IOException e) {
          System.out.println(e);
      }
  }

    static class Persona {

        private String name, city;
        private Integer age;

        public Persona(String line, String separator){
            String[] tokens = line.split(separator);
            int nTokens = tokens.length;
            this.name = null;
            this.city = null;
            this.age = null;

            if (nTokens>0 && !tokens[0].equals("")) this.name = tokens[0];
            if (nTokens>1 && !tokens[1].equals("")) this.city = tokens[1];
            if (nTokens>2 && !tokens[2].equals("")) this.age = Integer.parseInt(tokens[2]);
        }

        public String toString(){
            String res;
            res = "Nombre: " + Optional.ofNullable(name).orElse("Desconocido");
            res += ". Población: " + Optional.ofNullable(city).orElse("Desconocida");
            Optional<Integer> opcAge = Optional.ofNullable(age);
            res += ". Edad: "+ opcAge.map(Object::toString).orElse("Desconocida");
            return res;
        }

        public boolean isMinorOf(int limit){
            return (Optional.ofNullable(age).isPresent() && age < limit);
        }
    }

}
