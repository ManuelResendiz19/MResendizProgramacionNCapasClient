package com.MResendizProgramacionNCapas.Controller;

import com.MResendizProgramacionNCapas.ML.Result;
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
    
    
}
