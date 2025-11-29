package com.MResendizProgramacionNCapas.Controller;

import com.MResendizProgramacionNCapas.DTO.LoginRequest;
import com.MResendizProgramacionNCapas.ML.Colonia;
import com.MResendizProgramacionNCapas.ML.Direccion;
import com.MResendizProgramacionNCapas.ML.Estado;
import com.MResendizProgramacionNCapas.ML.Municipio;
import com.MResendizProgramacionNCapas.ML.Pais;
import com.MResendizProgramacionNCapas.ML.Result;
import com.MResendizProgramacionNCapas.ML.Rol;
import com.MResendizProgramacionNCapas.ML.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("usuario")// define la ruta base del controlador 
public class UsuarioController {

    private static final String urlBase= "http://localhost:8080";

    @GetMapping
    public String Index(Model model){
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<List<Usuario>>> responseEntity = restTemplate.exchange(urlBase + "/api/usuario", HttpMethod.GET
        ,HttpEntity.EMPTY, 
        new ParameterizedTypeReference<Result<List<Usuario>>>() {});
        
        if(responseEntity.getStatusCode().value() == 200){
            Result<List<Usuario>> result = responseEntity.getBody();
            model.addAttribute("usuarios", result.object);
            model.addAttribute("usuarioBusqueda", new Usuario());
        }  
    
        
        return "UsuarioIndex";
    }
    
    @PostMapping("/usuarioSearch")
    public String Index(@ModelAttribute Usuario usuario,Model model){
        
        model.addAttribute("usuarioBusqueda", new Usuario());
        
        String busqueda = usuario.getNombre();
        
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Usuario> requeEntity = new HttpEntity<>(usuario);
        
        ResponseEntity<Result<List<Usuario>>> responseEntity = restTemplate.exchange(urlBase + "/api/usuario/busqueda?busqueda=" + busqueda , HttpMethod.GET
        ,requeEntity, 
        new ParameterizedTypeReference<Result<List<Usuario>>>() {});
        
        if(responseEntity.getStatusCode().value() == 200){
            Result<List<Usuario>> result = responseEntity.getBody();
            model.addAttribute("usuarios", result.object);
        }  
        
        
        return "UsuarioIndex";
    }
    
    
    @GetMapping("/detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <Result<Usuario>> responseEntity = restTemplate.exchange(urlBase + "/api/usuario/" + IdUsuario, HttpMethod.GET
        ,HttpEntity.EMPTY, 
        new ParameterizedTypeReference<Result<Usuario>>() {});
        
        if(responseEntity.getStatusCode().value() == 200){
            Result<Usuario> result = responseEntity.getBody();
            model.addAttribute("usuario", result.object);
        }
        
        
        return "UsuarioDetail";
    }

    
   @PostMapping("/detail")
    public String Detail(@ModelAttribute Usuario usuario){
        RestTemplate restTemplate = new RestTemplate();
        
        HttpEntity<Usuario> requeEntity = new HttpEntity<>(usuario);
        
        ResponseEntity <Result<Usuario>> responseEntity = restTemplate.exchange(urlBase + "/api/usuario/update", HttpMethod.PUT, 
                requeEntity, new ParameterizedTypeReference<Result<Usuario>>() {
                });
        
        return "redirect:/usuario/detail/" + usuario.getIdUsuario();
    }
    
    @GetMapping("/add")
    public String Form(Model model){
        
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
      
        model.addAttribute("Usuario", usuario);
        
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<List<Pais>>> responseEntityP = restTemplate.exchange(urlBase + "/api/pais" , 
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result<List<Pais>>>() {
        });
        
        if(responseEntityP.getStatusCode().value() == 200){
            Result resultPais = (Result) responseEntityP.getBody();
            model.addAttribute("pais", resultPais.object);
        }
        
        ResponseEntity<Result<List<Rol>>> responseEntityR = restTemplate.exchange(urlBase + "/api/rol" , 
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result<List<Rol>>>() {
        });
        
        if(responseEntityR.getStatusCode().value() == 200){
            Result resultRol = (Result) responseEntityR.getBody();
            model.addAttribute("Rols", resultRol.object);
        }
        

        return "UsuarioForm";
    }
    
    
    @PostMapping("/add")
    public String Form(@ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result<List<Rol>>> responseRoles = restTemplate.exchange(
                urlBase + "/api/rol",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<List<Rol>>>() {
        });
        
        if (responseRoles.getStatusCode().is2xxSuccessful()) {
            Result resultRol = responseRoles.getBody();
            model.addAttribute("Rols", resultRol.object);
        }

        HttpEntity<Usuario> usuarioDirec = new HttpEntity<>(usuario);
        ResponseEntity<Usuario> responseEntityDi = restTemplate.exchange(urlBase + "/api/usuario/add", 
                HttpMethod.POST, usuarioDirec, new ParameterizedTypeReference<Usuario>(){
                });
        
        return "redirect:/usuario";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest()); 
        return "LoginForm"; 
    }
    
    @PostMapping("/login")
    public String Login(@ModelAttribute LoginRequest loginRequest, HttpSession session, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);

        ResponseEntity<Usuario> response = restTemplate.postForEntity(urlBase + "/api/auth/login", loginRequest, Usuario.class);

        session.setAttribute("usuario", response.getBody());
        
        Usuario usuario = response.getBody();
        if(usuario == null){
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "LoginForm";
        }
        
        
//        String rol = usuario.getRol();
//       if ("ADMIN".equalsIgnoreCase(rol)) {
//            return "redirect:/usuario";
//        } else {
//            return "redirect:/detail";
//        }
        return "redirect:/usuario";
    }
    
}
