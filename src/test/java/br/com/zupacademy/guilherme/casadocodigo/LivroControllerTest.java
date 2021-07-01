package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.controller.adviser.ValidationErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ValidationErrorHandler handler;

    @Test
    public void deveriaDevolver400CasoFalheEmAlgumaConstraint() throws Exception {
        URI uri = new URI("/autores");
        String titulo = "Senhor dos aneis";
        String resumo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in ";
        String sumario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        BigDecimal preco = new BigDecimal(1223.50);
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 01, 01);
        Integer idAutor = 1;
        Integer idCategoria = 1;

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectJson = mapper.createObjectNode();
        objectJson.put("titulo",titulo);
        objectJson.put("resumo", resumo);
        objectJson.put("sumario", sumario);
        objectJson.put("preco", preco);
        objectJson.put("paginas", paginas);
        objectJson.put("isbn", isbn);
        objectJson.put("dataPublicacao", "2022-01-01");
        objectJson.put("idAutor", 1);
        objectJson.put("idCategoria", 1);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectJson);
        System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(400));
    }

}
