package br.com.zupacademy.guilherme.casadocodigo;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.EstadoForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class EstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ObjectMapper jsonMapper;

    URI uri = new URI("/estados");

    public EstadoControllerTest() throws URISyntaxException {
    }

    @Test
    public void deveRetornar200AoCriarUmEstadoComSucesso() throws Exception {
        String nome = "Sao Paulo";
        String nomePais = "Brasil";

        Pais pais = new Pais(nomePais);
        em.persist(pais);

        EstadoForm estadoForm = new EstadoForm(nome, 1);
        String json = jsonMapper.writeValueAsString(estadoForm);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetonar400QuandoHouverJaUmEstadoComMesmoNomeNaquelePais() throws Exception {
        String nome = "Sao Paulo";
        String nomePais = "Brasil";

        Pais pais = new Pais(nomePais);
        em.persist(pais);

        EstadoForm estadoForm = new EstadoForm(nome, 1);
        String json = jsonMapper.writeValueAsString(estadoForm);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
