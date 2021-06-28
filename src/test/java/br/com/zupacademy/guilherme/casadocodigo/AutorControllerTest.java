package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.repository.AutorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void deveriaDevolver400CasoEmailEstejaEmFormatoIncorreto() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"teste\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornar200SeEstiverComFormCorretamentePreenchido() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"guilherme@gmail.com\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornar400QuandoDescricaoPossuiMaisDe400Caracteres() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"guilherme@gmail.com\", \"descricao\":\"" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
