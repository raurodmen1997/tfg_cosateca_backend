INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('admin','$2a$10$JREA1Di800SBOAH0KQm8D.mmRBULNXJcRjC1ST87CO7aXGr3fjAOK',0);
INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('raul','$2a$10$SV0ISoLXpFJGSD4B8hM46ePgfg80FQrKpcNTxyhVphwd/lanBlhmi',1);
INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('andrea','$2a$10$ODmqSqf3nCKN8isvI9cQB.6GGPVuaio4Q5VhRbvep90cXr13t5IVK',1);

INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Raul', 'Rodriguez', 'Mendez', '674761837', 'raulrodriguezmendez97@gmail.com', 2, 'NIF', '53346768Q', 41300, 1);
INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Andrea', 'Meca', 'Sanchez', '674761839', 'andrea.meca.sanchez@gmail.com', 3, 'NIF', '53346768Q', 41300, 0);


INSERT INTO `ayuntamientos` (`municipio`, `nombre`, `provincia`, `cuenta_id`, `direccion_email`) VALUES ('La rinconada','Ayuntamiento de la Rinconada', 'Sevilla', 1, ' info@aytolarinconada.es');

INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Lunes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('16:00:00', '20:00:00', 1, 'Lunes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Martes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('16:00:00', '20:00:00', 1, 'Martes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('16:00:00', '20:00:00', 1, 'Miercoles');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Jueves');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('16:00:00', '20:00:00', 1, 'Jueves');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Viernes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('16:00:00', '20:00:00', 1, 'Viernes');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Sábado');
INSERT INTO `horarios` (`hora_apertura`, `hora_cierre`, `ayuntamiento_id`, `dia`) VALUES ('08:00:00', '14:00:00', 1, 'Domingo');


INSERT INTO `ayuntamientos_codigos_postales` (`ayuntamiento_id`, `codigo_postal`) VALUES (1, 41300);
INSERT INTO `ayuntamientos_codigos_postales` (`ayuntamiento_id`, `codigo_postal`) VALUES (1, 41309);

INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('fav', 1);
INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('Para adquirir el mes que viene', 1);
INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('Para mi', 1);
INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('Para mi padre', 1);
INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('Para mi madre', 1);
INSERT INTO `listas_favoritos` (`nombre`, `usuario_id`) VALUES ('Tontos', 2);

