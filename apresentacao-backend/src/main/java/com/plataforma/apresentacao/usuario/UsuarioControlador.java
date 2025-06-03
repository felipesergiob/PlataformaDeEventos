package com.plataforma.apresentacao.usuario;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private @Autowired final UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.POST)
    public UsuarioResponseDTO registrar(@RequestBody RegistroDTO registroDTO) {
        return usuarioService.registrar(registroDTO);
    }

    @RequestMapping(method = RequestMethod.POST)
    public UsuarioResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO);
    }
}
