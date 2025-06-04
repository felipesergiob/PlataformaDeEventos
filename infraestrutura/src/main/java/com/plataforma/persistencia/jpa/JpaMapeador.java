package com.plataforma.persistencia.jpa;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.usuario.Usuario;
import com.plataforma.aplicacao.usuario.UsuarioResumo;
import com.plataforma.aplicacao.usuario.UsuarioResumoImpl;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.stereotype.Component;

@Component
class JpaMapeador extends ModelMapper {

    JpaMapeador() {
        var configuracao = getConfiguration();
        configuracao.setFieldMatchingEnabled(true)
                   .setFieldAccessLevel(AccessLevel.PRIVATE)
                   .setSkipNullEnabled(true);

        addConverter(new AbstractConverter<UsuarioJpa, Usuario>() {
            @Override
            protected Usuario convert(UsuarioJpa source) {
                var id = map(source.getId(), UsuarioId.class);
                return new Usuario(id, source.getNome(), source.getEmail(), source.getSenha(), 
                                 source.getTelefone(), source.getFotoPerfil());
            }
        });

        addConverter(new AbstractConverter<UsuarioJpa, UsuarioResumoImpl>() {
            @Override
            protected UsuarioResumoImpl convert(UsuarioJpa source) {
                var resumo = new UsuarioResumoImpl();
                resumo.setId(String.valueOf(source.getId()));
                resumo.setNome(source.getNome());
                resumo.setEmail(source.getEmail());
                resumo.setSenha(source.getSenha());
                return resumo;
            }
        });

        addConverter(new AbstractConverter<UsuarioResumo, UsuarioJpa>() {
            @Override
            protected UsuarioJpa convert(UsuarioResumo source) {
                var jpa = new UsuarioJpa();
                jpa.setId(source.getId() != null ? Integer.parseInt(source.getId()) : null);
                jpa.setNome(source.getNome());
                jpa.setEmail(source.getEmail());
                jpa.setSenha(source.getSenha());
                return jpa;
            }
        });

        addConverter(new AbstractConverter<Integer, UsuarioId>() {
            @Override
            protected UsuarioId convert(Integer source) {
                return new UsuarioId(source);
            }
        });

        addConverter(new AbstractConverter<UsuarioJpa, UsuarioId>() {
            @Override
            protected UsuarioId convert(UsuarioJpa source) {
                return map(source.getId(), UsuarioId.class);
            }
        });

        createTypeMap(UsuarioJpa.class, UsuarioResumo.class)
            .setConverter(context -> {
                var source = context.getSource();
                var resumo = new UsuarioResumoImpl();
                resumo.setId(String.valueOf(source.getId()));
                resumo.setNome(source.getNome());
                resumo.setEmail(source.getEmail());
                resumo.setSenha(source.getSenha());
                return resumo;
            });
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if (source != null && destinationType.equals(UsuarioResumo.class)) {
            var resumo = new UsuarioResumoImpl();
            if (source instanceof UsuarioJpa jpa) {
                resumo.setId(String.valueOf(jpa.getId()));
                resumo.setNome(jpa.getNome());
                resumo.setEmail(jpa.getEmail());
                resumo.setSenha(jpa.getSenha());
            }
            return (D) resumo;
        }
        return source != null ? super.map(source, destinationType) : null;
    }
}