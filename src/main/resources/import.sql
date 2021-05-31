INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('admin','$2a$10$JREA1Di800SBOAH0KQm8D.mmRBULNXJcRjC1ST87CO7aXGr3fjAOK',0);


INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('raul','$2a$10$SV0ISoLXpFJGSD4B8hM46ePgfg80FQrKpcNTxyhVphwd/lanBlhmi',1);
INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('andrea','$2a$10$ODmqSqf3nCKN8isvI9cQB.6GGPVuaio4Q5VhRbvep90cXr13t5IVK',1);
INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('fatima','$$2a$10$sARHJgqrd6E7MAazWzZfy.lci82RAWUPe/muEwKJtP9aowvjudTRa',1);
INSERT INTO `cuentas` (`nombre_perfil`, `pass`, `autoridad`) VALUES ('antonio','$2a$10$o0Pr2f0BgYxxqCF8KpTQAe5FIq2A.Dvshb2XMsFfw.m3OD59WDSBO',1);


INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Raul', 'Rodriguez', 'Mendez', '674761837', 'raulrodriguezmendez97@gmail.com', 2, 'NIF', '53346768Q', 41300, 1);
INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Andrea', 'Meca', 'Sanchez', '674761839', 'andrea.meca.sanchez@gmail.com', 3, 'NIF', '53346798Q', 41300, 0);
INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Fátima', 'Méndez', 'Bellido', '674761814', 'fatimamendez97@gmail.com', 4, 'NIF', '53346769Q', 41309, 1);
INSERT INTO `usuarios` (`nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion_email`, `cuenta_id`, `tipo_identificacion`, `codigo_identificacion`, `codigo_postal`, `olvidado`) VALUES ('Antonio', 'Rodriguez', 'Ariza', '674769937', 'antoniorodriguez97@gmail.com', 5, 'NIF', '53346468Q', 41300, 1);


INSERT INTO `ayuntamientos` (`municipio`, `nombre`, `provincia`, `cuenta_id`, `direccion_email`, `telefono`, `direccion`) VALUES ('La rinconada','Ayuntamiento de la Rinconada', 'Sevilla', 1, 'raulrodriguezmendez1997@gmail.com', '954791405', 'Plaza Juan Ramón Jiménez, 2');

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

