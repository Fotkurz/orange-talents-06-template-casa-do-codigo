package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.controller.dto.ErroDeFormularioDto;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.ClienteForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Estado;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @BeforeAll
    @Transactional
    public void testSetUp() {
        em.createQuery("DELETE FROM {spring.datasource.url}");
    }

    public ObjectMapper jsonMapper = new ObjectMapper();

    private URI uri = new URI("/clientes");

    public ClienteControllerTest() throws URISyntaxException {
    }


    @Test
    public void deveriaRetornar200AoCadastrarUmClienteComParametrosValidos() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        Integer idPais = 1;
        Integer idEstado = 1;
        String telefone = "1432325000";
        String cep = "17000-015";

        Pais pais = new Pais("Brasil");
        Estado estado = new Estado("Sao Paulo", pais);

        em.persist(pais);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais, idEstado,
                telefone, cep);

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(clienteJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornarUmJsonComOIdDoClienteAoCadastrarCorretamente() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        Integer idPais = 1;
        Integer idEstado = 1;
        String telefone = "1432325000";
        String cep = "17000-015";

        Pais pais = new Pais("Brasil");
        Estado estado = new Estado("Sao Paulo", pais);

        em.persist(pais);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais, idEstado,
                telefone, cep);

        String json = "{\"id\":1}";

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    public void deveRetornar200AoCadastrarUmClienteDeUmPaisSemEstadoCadastrado() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        Integer idPais = 1;
        String telefone = "1432325000";
        String cep = "17000-015";

        Pais pais = new Pais("Brasil");

        em.persist(pais);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais,
                telefone, cep);

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornar400AoCadastrarUmClienteEmUmPaisSemInformarOEstado() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        Integer idPais = 1;
        String telefone = "1432325000";
        String cep = "17000-015";

        Pais pais = new Pais("Brasil");
        Estado estado = new Estado("Sao Paulo", pais);

        em.persist(pais);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais,
                telefone, cep);

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornar400AoCadastrarClienteCujoEstadoNaoExisteNoPais() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        String telefone = "1432325000";
        String cep = "17000-015";
        Integer idPais = 2;
        Integer idEstado = 1;

        Pais pais1 = new Pais("Brasil");
        Pais pais2 = new Pais("Argentina");
        Estado estado = new Estado("Sao Paulo", pais1);
        em.persist(pais1);
        em.persist(pais2);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais,idEstado,
                telefone, cep);

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornarErroDtoCujoEstadoNaoExistaParaAquelePais() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        String telefone = "1432325000";
        String cep = "17000-015";
        Integer idPais = 2;
        Integer idEstado = 1;

        Pais pais1 = new Pais("Brasil");
        Pais pais2 = new Pais("Argentina");
        Estado estado = new Estado("Sao Paulo", pais1);
        em.persist(pais1);
        em.persist(pais2);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document, endereco, complemento, cidade, idPais,idEstado,
                telefone, cep);

        ErroDeFormularioDto erroDto = new ErroDeFormularioDto("estado", "Invalido ou inexistente");
        String jsonErro = jsonMapper.writeValueAsString(erroDto);
        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        System.out.println(clienteJson);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().json(jsonErro));
    }
    @Test
    public void deveRetornar400CasoDocumentoJaExista() throws Exception {
        String email = "jose@gmail.com";
        String nome = "Jose";
        String sobrenome = "Silva";
        String document1 = "29.045.322/0001-39";
        String endereco = "Rua treze";
        String complemento = "240";
        String cidade = "Springfield";
        Integer idPais = 1;
        Integer idEstado = 1;
        String telefone = "1432325000";
        String cep = "17000-015";

        String email2 = "joao@gmail.com";
        String nome2 = "Joao";
        String sobrenome2 = "Silva";
        String document2 = "29.045.322/0001-39";
        String endereco2 = "Rua treze";
        String complemento2 = "240";
        String telefone2 = "143049104310";
        String cep2 = "17000-016";

        Pais pais = new Pais("Brasil");
        Estado estado = new Estado("Sao Paulo", pais);

        em.persist(pais);
        em.persist(estado);

        ClienteForm clienteForm = new ClienteForm(email, nome, sobrenome,
                document1, endereco, complemento, cidade, idPais, idEstado,
                telefone, cep);

        ClienteForm clienteForm2 = new ClienteForm(email2, nome2, sobrenome2,
                document2, endereco2, complemento2, cidade, idPais, idEstado,
                telefone2, cep2);

        String clienteJson = jsonMapper.writeValueAsString(clienteForm);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson)
                .contentType(MediaType.APPLICATION_JSON));

        String clienteJson2 = jsonMapper.writeValueAsString(clienteForm2);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(clienteJson2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
