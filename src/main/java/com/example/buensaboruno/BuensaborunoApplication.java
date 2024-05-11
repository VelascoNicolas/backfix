package com.example.buensaboruno;

import com.example.buensaboruno.domain.entities.*;
import com.example.buensaboruno.domain.enums.*;
import com.example.buensaboruno.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;


@SpringBootApplication
public class BuensaborunoApplication {private static final Logger logger = LoggerFactory.getLogger(BuensaborunoApplication.class);

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ImagenClienteRepository imagenPersonaRepository;
	@Autowired
	private PromocionDetalleRepository promocionDetalleRepository;

	@Autowired
	private UsuarioEmpleadoRepository usuarioEmpleadoRepository;

	@Autowired
	private UsuarioClienteRepository usuarioClienteRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private DomicilioRepository domicilioRepository;

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ArticuloInsumoRepository articuloInsumoRepository;

	@Autowired
	private ArticuloManufacturadoRepository articuloManufacturadoRepository;

	@Autowired
	private ImagenArticuloRepository imagenArticuloRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(BuensaborunoApplication.class, args);
		logger.info("Estoy activo en el main");
	}

	@Bean
	@Transactional
	CommandLineRunner init(ClienteRepository clienteRepository,
						   ImagenClienteRepository imagenClienteRepository,
						   ImagenPromocionRepository imagenPromocionRepository,
						   ImagenEmpleadoRepository imagenEmpleadoRepository,
						   PromocionDetalleRepository promocionDetalleRepository,
						   UsuarioClienteRepository usuarioClienteRepository,
						   UsuarioEmpleadoRepository usuarioEmpleaoRepository,
						   PaisRepository paisRepository,
						   ProvinciaRepository provinciaRepository,
						   LocalidadRepository localidadRepository,
						   EmpresaRepository empresaRepository,
						   SucursalRepository sucursalRepository,
						   DomicilioRepository domicilioRepository,
						   UnidadMedidaRepository unidadMedidaRepository,
						   CategoriaRepository categoriaRepository,
						   ArticuloInsumoRepository articuloInsumoRepository,
						   ArticuloManufacturadoRepository articuloManufacturadoRepository,
						   ImagenArticuloRepository imagenArticuloRepository,
						   PromocionRepository promocionRepository,
						   PedidoRepository pedidoRepository,
						   EmpleadoRepository empleadoRepository, FacturaRepository facturaRepository) {
		return args -> {
			logger.info("----------------ESTOY----FUNCIONANDO---------------------");
			// Etapa del dashboard
			// Crear 1 pais
			// Crear 2 provincias para ese pais
			// crear 2 localidades para cada provincia
			Pais pais1 = Pais.builder().nombre("Argentina").build();
			paisRepository.save(pais1);
			//CREACION DE PROVINCIAS
			Provincia provincia1 = Provincia.builder().nombre("Mendoza").pais(pais1).build();
			Provincia provincia2 = Provincia.builder().nombre("Buenos Aires").pais(pais1).build();
			provinciaRepository.save(provincia1);
			provinciaRepository.save(provincia2);

			//CREACION DE LOCALIDADES
			Localidad localidad1 = Localidad.builder().nombre("Lujan de Cuyo").provincia(provincia1).build();
			Localidad localidad2 = Localidad.builder().nombre("Guaymallen").provincia(provincia1).build();
			Localidad localidad3 = Localidad.builder().nombre("San Martin").provincia(provincia2).build();


			localidadRepository.save(localidad1);
			localidadRepository.save(localidad2);
			localidadRepository.save(localidad3);


			// Crear 1 empresa, 2 sucursales para esa empresa y los Domicilios para esas sucursales

			Empresa empresaIanchina = Empresa.builder().nombre("Lo de Ianchina").cuil(30546780L).razonSocial("Venta de Alimentos").build();
			empresaRepository.save(empresaIanchina);

			Sucursal sucursalLujan = Sucursal.builder().
					nombre("En Lujan de Cuyo").horarioApertura(LocalTime.of(12,0)).
					horarioCierre(LocalTime.of(23,30)).
					build();

			Sucursal sucursalSanMartin = Sucursal.builder().nombre("En Gral San Martin").
					horarioApertura(LocalTime.of(20,0)).
					horarioCierre(LocalTime.of(0,30)).build();

			Domicilio domicilioBerutti = Domicilio.builder().cp(5519).calle("Berutti").numero(2684).piso(0).nroDpto(5).
					localidad(localidad1).build();

			Domicilio domicilioGaboto = Domicilio.builder().cp(7600).calle("Gaboto").numero(3475).
					localidad(localidad3).build();
			// GRABAMOS DOMICILIOS
			domicilioRepository.save(domicilioBerutti);
			domicilioRepository.save(domicilioGaboto);

			//ASOCIAMOS LOS DOMICILIOS A SUCURSAL
			sucursalLujan.setDomicilio(domicilioBerutti);
			sucursalSanMartin.setDomicilio(domicilioGaboto);

			//ASOCIAMOS SUCURSALES A EMPRESA
			empresaIanchina.getSucursales().add(sucursalLujan);
			empresaIanchina.getSucursales().add(sucursalSanMartin);

			//ASIGNAMOS EMPRESA A SUCURSALES
			sucursalLujan.setEmpresa(empresaIanchina);
			sucursalSanMartin.setEmpresa(empresaIanchina);
			// Grabo las sucursales
			sucursalRepository.save(sucursalLujan);
			sucursalRepository.save(sucursalSanMartin);
			// Grabi empresa
			empresaRepository.save(empresaIanchina);

			// Crear Categorías de productos y subCategorías de los mismos
			Categoria categoriaBebidas = Categoria.builder().denominacion("Bebidas").
					build();
			categoriaRepository.save(categoriaBebidas);

			Categoria categoriaGaseosas = Categoria.builder().denominacion("Gaseosas").
					build();
			categoriaRepository.save(categoriaGaseosas);

			Categoria categoriaTragos = Categoria.builder().denominacion("Tragos").
					build();
			categoriaRepository.save(categoriaTragos);

			Categoria categoriaHamburguesas = Categoria.builder().denominacion("Hamburguesas").
					build();

			Categoria categoriaInsumos = Categoria.builder().denominacion("Insumos").
					build();

			// Grabo la categoría de insumos y de Manufacturados
			categoriaRepository.save(categoriaHamburguesas);
			categoriaRepository.save(categoriaInsumos);
			// Asigno subCategorías

			categoriaBebidas.getSubCategorias().add(categoriaGaseosas);
			categoriaBebidas.getSubCategorias().add(categoriaTragos);
			// Grabo las Subcategorías
			categoriaRepository.save(categoriaBebidas);

			logger.info("---------------voy a asignar a Lujan de Cuyo--------------------");
			//ASOCIAMOS CATEGORIAS CON SUCURSAL
			categoriaInsumos.getSucursales().add(sucursalLujan);
			// Cargo las categorias a la sucursal guaymallen
			sucursalLujan.getCategorias().add(categoriaInsumos);
			sucursalLujan.getCategorias().add(categoriaBebidas);
			sucursalLujan.getCategorias().add(categoriaGaseosas);
			sucursalLujan.getCategorias().add(categoriaTragos);
			sucursalLujan.getCategorias().add(categoriaHamburguesas);
			logger.info("{}",sucursalLujan);
			// Grabo las categorias que vende esa sucursal
			sucursalRepository.save(sucursalLujan);

			logger.info("---------------saque el save de abajo-------------------");


			logger.info("---------------grabe Lujan--------------------");

			logger.info("---------------voy a asignar a San Martin--------------------");
			categoriaInsumos.getSucursales().add(sucursalSanMartin);
			// Cargo las categorias a la sucursal Mardel Plata
			sucursalSanMartin.getCategorias().add(categoriaInsumos);
			sucursalSanMartin.getCategorias().add(categoriaBebidas);
			sucursalSanMartin.getCategorias().add(categoriaGaseosas);
			sucursalSanMartin.getCategorias().add(categoriaTragos);
			sucursalSanMartin.getCategorias().add(categoriaHamburguesas);
// Grabo las categorias que vende esa sucursal
			sucursalRepository.save(sucursalSanMartin);

			logger.info("---------------grabe San Martin--------------------");




			// Crear Unidades de medida
			UnidadMedida unidadMedidaLitros = UnidadMedida.builder().denominacion("Litros").build();
			UnidadMedida unidadMedidaGramos = UnidadMedida.builder().denominacion("Gramos").build();
			UnidadMedida unidadMedidaCantidad = UnidadMedida.builder().denominacion("Cantidad").build();
			UnidadMedida unidadMedidaPorciones = UnidadMedida.builder().denominacion("Porciones").build();
			unidadMedidaRepository.save(unidadMedidaLitros);
			unidadMedidaRepository.save(unidadMedidaGramos);
			unidadMedidaRepository.save(unidadMedidaCantidad);
			unidadMedidaRepository.save(unidadMedidaPorciones);



			// Crear Insumos , coca cola , harina , etc
			ArticuloInsumo cocaCola = ArticuloInsumo.builder().
					denominacion("Coca cola").
					unidadMedida(unidadMedidaLitros).
					esParaElaborar(false).
					stockActual(5).
					stockMaximo(50).
					precioCompra(700.0).
					precioVenta(1500.0).
					build();
			ArticuloInsumo panHamburguesa = ArticuloInsumo.builder().denominacion("Pan de Hamburguesa").unidadMedida(unidadMedidaGramos).esParaElaborar(true).stockActual(4).stockMaximo(40).precioCompra(40.0).precioVenta(60.5).build();
			ArticuloInsumo queso = ArticuloInsumo.builder().denominacion("Queso").unidadMedida(unidadMedidaGramos).esParaElaborar(true).stockActual(20).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();
			ArticuloInsumo tomate = ArticuloInsumo.builder().denominacion("Tomate").unidadMedida(unidadMedidaCantidad).esParaElaborar(true).stockActual(20).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();
			ArticuloInsumo carne = ArticuloInsumo.builder().denominacion("Carne Vacuna").unidadMedida(unidadMedidaGramos).esParaElaborar(true).stockActual(5000).stockMaximo(10000).precioCompra(3100.0).precioVenta(5000.0).build();

			// crear fotos para cada insumo
			ImagenArticulo imagenArticuloCoca = ImagenArticulo.builder().
					url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg").
					build();
			ImagenArticulo imagenCarneMolida = ImagenArticulo.builder().url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQC2nNWuUms_jrGsfg2lN_ac0JoUz36r2r3pnD4HCZH-j8wIckKx5lH44D9rYkNFPFd3D0&usqp=CAU").build();
			ImagenArticulo imagenArticuloPanHamburguesa = ImagenArticulo.builder().url("https://jumboargentina.vtexassets.com/arquivos/ids/799939/Pan-Hamburguesa-Artesano-Bimbo-X-240g-Pan-De-Hamburguesa-Bimbo-Artesano-4u-1-942990.jpg?v=638349573985000000").build();
			ImagenArticulo imagenArticuloQueso = ImagenArticulo.builder().url("https://superdepaso.com.ar/wp-content/uploads/2021/06/SANTAROSA-PATEGRAS-04.jpg").build();
			ImagenArticulo imagenArticuloTomate = ImagenArticulo.builder().url("https://thefoodtech.com/wp-content/uploads/2020/06/Componentes-de-calidad-en-el-tomate-828x548.jpg").build();
			imagenArticuloRepository.save(imagenArticuloCoca);
			imagenArticuloRepository.save(imagenCarneMolida);
			imagenArticuloRepository.save(imagenArticuloPanHamburguesa);
			imagenArticuloRepository.save(imagenArticuloQueso);
			imagenArticuloRepository.save(imagenArticuloTomate);

			//ASOCIAMOS IMAGEN CON INSUMOS
			cocaCola.getImagenes().add(imagenArticuloCoca);
			panHamburguesa.getImagenes().add(imagenArticuloPanHamburguesa);
			queso.getImagenes().add(imagenArticuloQueso);
			tomate.getImagenes().add(imagenArticuloTomate);
			carne.getImagenes().add(imagenCarneMolida);
			// Grabamos los Articulos
			articuloInsumoRepository.save(carne);
			articuloInsumoRepository.save(cocaCola);
			articuloInsumoRepository.save(panHamburguesa);
			articuloInsumoRepository.save(queso);
			articuloInsumoRepository.save(tomate);


			//ASOCIAMOS CATEGORIA CON INSUMOS
			categoriaInsumos.addBoth(panHamburguesa);
			categoriaInsumos.addBoth(carne);
			categoriaInsumos.addBoth(queso);
			categoriaInsumos.addBoth(tomate);
			categoriaGaseosas.addBoth(cocaCola);
			categoriaRepository.save(categoriaInsumos);
			categoriaRepository.save(categoriaGaseosas);

			// Crear Articulos Manufacturados
			ArticuloManufacturado hamburguesaSimple = ArticuloManufacturado.builder().
					denominacion("Hamburguesa Simple").
					descripcion("Una Hamburguesa clasica").
					unidadMedida(unidadMedidaCantidad).
					precioVenta(130.0).
					tiempoEstimadoMinutos(15).
					preparacion("Pasos de preparacion de una muzza de toda la vida").
					build();
			ArticuloManufacturado hamburguesaCompleta = ArticuloManufacturado.builder().denominacion("Hamburguesa Completa").descripcion("Una Hamburguesa bien completa").unidadMedida(unidadMedidaCantidad).precioVenta(150.0).tiempoEstimadoMinutos(30).preparacion("Pasos de preparacion de una Hamburguesa Completa").build();

			// Crear fotos para los artículos manufacturados
			ImagenArticulo imagenArticuloHambuSimple = ImagenArticulo.builder().
					url("https://cache-backend-mcd.mcdonaldscupones.com/media/image/product$kqXzqebG/200/200/original?country=ar")
					.build();
			ImagenArticulo imagenArticuloHambuCompleta = ImagenArticulo.builder()
					.url("https://cache-backend-mcd.mcdonaldscupones.com/media/image/product$kqX8TYcp/200/200/original?country=ar")
					.build();
			imagenArticuloRepository.save(imagenArticuloHambuSimple);
			imagenArticuloRepository.save(imagenArticuloHambuCompleta);

			//ASOCIAMOS IMAGEN CON ARTICULO MANUFACTURADO
			hamburguesaCompleta.getImagenes().add(imagenArticuloHambuCompleta);
			hamburguesaSimple.getImagenes().add(imagenArticuloHambuSimple);
			articuloManufacturadoRepository.save(hamburguesaCompleta);
			articuloManufacturadoRepository.save(hamburguesaSimple);

			// Establecer las relaciones entre estos objetos Articulos de la Receta independiente
			ArticuloManufacturadoDetalle detalle1 = ArticuloManufacturadoDetalle.builder().
					articuloInsumo(carne).
					cantidad(300).
					build();
			ArticuloManufacturadoDetalle detalle2 = ArticuloManufacturadoDetalle.builder().articuloInsumo(queso).cantidad(600).build();
			ArticuloManufacturadoDetalle detalle3 = ArticuloManufacturadoDetalle.builder().articuloInsumo(panHamburguesa).cantidad(2).build();
			ArticuloManufacturadoDetalle detalle4 = ArticuloManufacturadoDetalle.builder().articuloInsumo(queso).cantidad(650).build();
			ArticuloManufacturadoDetalle detalle5 = ArticuloManufacturadoDetalle.builder().articuloInsumo(tomate).cantidad(2).build();
			// grabamos el Artículo Manufacturado
			articuloManufacturadoDetalleRepository.save(detalle1);
			articuloManufacturadoDetalleRepository.save(detalle2);
			articuloManufacturadoDetalleRepository.save(detalle3);
			articuloManufacturadoDetalleRepository.save(detalle4);
			articuloManufacturadoDetalleRepository.save(detalle5);

			//ASOCIAMOS LOS DETALLE MANUFACTURADO AL ARTICULO MANUFACTURADO - LA RECETA
			hamburguesaSimple.getArticuloManufacturadoDetalles().add(detalle1);
			hamburguesaSimple.getArticuloManufacturadoDetalles().add(detalle3);

			hamburguesaCompleta.getArticuloManufacturadoDetalles().add(detalle1);
			hamburguesaCompleta.getArticuloManufacturadoDetalles().add(detalle2);
			hamburguesaCompleta.getArticuloManufacturadoDetalles().add(detalle3);
			hamburguesaCompleta.getArticuloManufacturadoDetalles().add(detalle4);
			hamburguesaCompleta.getArticuloManufacturadoDetalles().add(detalle5);
			// GRABAMOS LA RECETA
			articuloManufacturadoRepository.save(hamburguesaSimple);
			articuloManufacturadoRepository.save(hamburguesaCompleta);

			// Establecer relaciones de las categorias - Cada Producto Manufacturado Pertenece a una categoria

			categoriaHamburguesas.addBoth(hamburguesaSimple);
			categoriaHamburguesas.addBoth(hamburguesaCompleta);
			// Graba la categoria y los productos asociados
			categoriaRepository.save(categoriaHamburguesas);

			// Crear promocion para sucursal - Dia de los enamorados
			// Tener en cuenta que esa promocion es exclusivamente para una sucursal determinada d euna empresa determinada
			Promocion promocionDiaEnamorados = Promocion.builder().denominacion("Dia de los Enamorados")
					.fechaDesde(LocalDate.of(2024,2,13))
					.fechaHasta(LocalDate.of(2024,2,15))
					.horaDesde(LocalTime.of(0,0))
					.horaHasta(LocalTime.of(23,59))
					.descripcionDescuento("El descuento que se hace por san valentin, un dia antes y un dia despues")
					.precioPromocional(100.0)
					.tipoPromocion(TipoPromocion.PROMOCION)
					.build();
			// Agregamos los Manufacturados y alguna bebida que figura como insumo
			PromocionDetalle detallePromo1=PromocionDetalle.builder().cantidad(2).articulo(hamburguesaCompleta).build();

			PromocionDetalle detallePromo2=PromocionDetalle.builder().cantidad(1).articulo(cocaCola).build();




			promocionDiaEnamorados.getPromocionDetalles().add(detallePromo1);
			promocionDiaEnamorados.getPromocionDetalles().add(detallePromo2);

			promocionRepository.save(promocionDiaEnamorados);

			Promocion simpleConCoca = Promocion.builder().denominacion("simple + coca")
					.fechaDesde(LocalDate.of(2024,2,13))
					.fechaHasta(LocalDate.of(2024,2,15))
					.horaDesde(LocalTime.of(0,0))
					.horaHasta(LocalTime.of(23,59))
					.descripcionDescuento("hamburguesa simple + Coca Cola 1.5lts")
					.precioPromocional(100.0)
					.tipoPromocion(TipoPromocion.PROMOCION)
					.build();
			// Agregamos los Manufacturados y alguna bebida que figura como insumo
			PromocionDetalle detallePromo3=PromocionDetalle.builder().cantidad(1).articulo(hamburguesaSimple).build();

			PromocionDetalle detallePromo4=PromocionDetalle.builder().cantidad(2).articulo(cocaCola).build();
			promocionDiaEnamorados.getPromocionDetalles().add(detallePromo3);
			promocionDiaEnamorados.getPromocionDetalles().add(detallePromo4);

			promocionRepository.save(simpleConCoca);

			// Sucursal San Martin
			Sucursal sucursalId1 = sucursalRepository.findWithPromocionesById(1L);
			Sucursal sucursalId2 = sucursalRepository.findWithPromocionesById(2L);
			Promocion promocionId1 = promocionRepository.findAllWithSucursales(1L);
			Promocion promocionId2 = promocionRepository.findAllWithSucursales(2L);
			sucursalId1.getPromociones().add(promocionId1);
			sucursalId1.getPromociones().add(promocionId2);
			promocionId1.getSucursales().add(sucursalId1);
			promocionId1.getSucursales().add(sucursalId2);
			sucursalRepository.save(sucursalId1);
			sucursalRepository.save(sucursalId2);
			promocionRepository.save(promocionId1);
			promocionRepository.save(promocionId2);
			logger.info("---------------Promociones en sucursal id = 1---------------");
			sucursalId1.getPromociones()
					.stream()
					.map(Promocion::getDenominacion)
					.forEach(logger::info);
			logger.info("---------------Sucursales con la promocion id = 1---------------");
			promocionId1.getSucursales()
					.stream()
					.map(Sucursal::getNombre)
					.forEach(logger::info);
			logger.info("----------------------------------------------------------------");

			//Crea un cliente y un usuario
			ImagenCliente imagenCliente = ImagenCliente.builder().url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuR_n7Ycyy-62Hc3OcezRMatpd9jEOT9UgNA&s").build();
			imagenClienteRepository.save(imagenCliente);
			ImagenEmpleado imagenEmpleado = ImagenEmpleado.builder().url("https://www.mdzol.com/u/fotografias/m/2023/9/6/f768x1-1472023_1472150_4630.png").build();
			imagenEmpleadoRepository.save(imagenEmpleado);
			Domicilio domicilioCliente = Domicilio.builder().cp(5519).calle("Picket Post Close").numero(12).piso(0).nroDpto(1).localidad(localidad1).build();
			domicilioRepository.save(domicilioCliente);
			UsuarioCliente usuarioCliente = UsuarioCliente.builder().userName("RubeusH").auth0Id("9565a49d-ecc1-4f4e-adea-6cdcb7edc4a3").build();
			usuarioClienteRepository.save(usuarioCliente);
			UsuarioEmpleado usuarioEmpleado = UsuarioEmpleado.builder().userName("elPeluca").auth0Id("9565a49d-ecc1-4f4e-adea-6cdcb7edc43a").build();
			usuarioEmpleadoRepository.save(usuarioEmpleado);

			Cliente cliente1 = Cliente.builder()
					.imagenCliente(imagenCliente)
					.email("hagrid@hogwarts.com")
					.nombre("Rubeus")
					.apellido("Hagrid")
					.usuario(usuarioCliente)
					.telefono("2615920825")
			.build();

			cliente1.getDomicilios().add(domicilioCliente);
			clienteRepository.save(cliente1);

			LocalDate date = LocalDate.of(1996,06,30);
			Empleado empleado = Empleado.builder()
					.email("correoFalso@hotmail.com")
					.tipoEmpleado(Rol.CAJERO)
					.nombre("CorreoFalso")
					.apellido("Falsin")
					.usuarioEmpleado(usuarioEmpleado)
					.telefono("2612151170")
					.fechaNacimiento(date)
					.imagenEmpleado(imagenEmpleado)
					.sucursal(sucursalLujan)
			.build();
			sucursalLujan.getEmpleados().add(empleado);
			empleadoRepository.save(empleado);
			logger.info("Empleado{}:",empleado);

			//Crea un pedido para el cliente
			Pedido pedido = Pedido.builder().fechaPedido(LocalDate.now())
					.horaEstimadaFinalizacion(LocalTime.now())
					.total(300.0)
					.totalCosto(170.6)
					.estado(Estado.PREPARACION)
					.formaPago(FormaPago.MERCADO_PAGO)
					.tipoEnvio(TipoEnvio.DELIVERY)
					.sucursal(sucursalLujan)
					.domicilio(domicilioCliente)
					.build();

			DetallePedido detallePedido1 = DetallePedido.builder().articulo(hamburguesaCompleta).cantidad(1).subTotal(200.0).build();
			DetallePedido detallePedido2 = DetallePedido.builder().articulo(cocaCola).cantidad(2).subTotal(100.0).build();

			pedido.getDetallePedidos().add(detallePedido1);
			pedido.getDetallePedidos().add(detallePedido2);
			pedido.setCliente(cliente1);
			pedido.setEmpleado(empleado);
			pedidoRepository.save(pedido);

			Random random = new Random();
			Factura facturaBuilder = Factura.builder().fechaFcturacion(LocalDate.now())
					.mpPaymentId(random.nextInt(1000))  // Se asume un rango máximo de 1000
					.mpMerchantOrderId(random.nextInt(1000)) // Se asume un rango máximo de 1000
					.mpPreferenceId("MP-" + random.nextInt(10000))  // Se asume un rango máximo de 10000
					.mpPaymentType("Tipo" + random.nextInt(10)) // Se asume un rango máximo de 10
					.formaPago(FormaPago.EFECTIVO)
					.totalVenta(random.nextDouble() * 1000).build();

			facturaRepository.save(facturaBuilder);

			pedido.setFactura(facturaBuilder);

			pedidoRepository.save(pedido);


			//Prueba de carga perezosa
			//Empresa-Sucursal
			//Sucursal-Promocion
			//Sucursal-Categoria
			//Sucursal-Empleado

			Domicilio domicilioSucu1 = Domicilio.builder().cp(5519).calle("calle1").numero(2684).piso(0).nroDpto(5).
					localidad(localidad1).build();

			domicilioRepository.save(domicilioSucu1);

			Domicilio domicilioSucu2 = Domicilio.builder().cp(5519).calle("calle2").numero(2684).piso(0).nroDpto(5).
					localidad(localidad1).build();
			domicilioRepository.save(domicilioSucu2);

			Sucursal sucursal = Sucursal.builder()
					.nombre("sucursal prueba")
					.domicilio(domicilioSucu1)
					.horarioApertura(LocalTime.of(12,30,00))
					.horarioApertura(LocalTime.of(20,00,00))
					.build();
			sucursalRepository.save(sucursal);
			Sucursal sucursal2 = Sucursal.builder()
					.nombre("sucursal prueba2")
					.domicilio(domicilioSucu2)
					.horarioApertura(LocalTime.of(12,30,00))
					.horarioApertura(LocalTime.of(20,00,00))
					.build();
			sucursalRepository.save(sucursal2);

			Empresa empresa = Empresa.builder()
					.nombre("Empresa de prueba")
					.cuil(999999999L)
					.razonSocial("Razon social")
					.build();
			empresaRepository.save(empresa);

			Categoria categoria = Categoria.builder()
					.denominacion("Categoria de prueba")
					.build();
			categoriaRepository.save(categoria);

			var categoriaRep = categoriaRepository.findWithSucursalesById(6L);//CON FINDBYID NO SE PUEDE AÑADIR SUCURSALES POR LAZY
			var empresaRepo = empresaRepository.findWithSucursalesById(2L);
			Sucursal sucursalRepo = sucursalRepository.findWithEmpleadosById(3L);//CON FINDBYID NO SE PUEDE AÑADIR EMPLEADOS POR LAZY
			Optional<Sucursal> sucursalRepo2 = sucursalRepository.findById(4L);
			Sucursal sucursalRepo3 = sucursalRepository.findWithCategoriasById(3L);//CON FINDBYID NO SE PUEDE AÑADIR CATEGORIAS POR LAZY
			if(sucursalRepo2.isPresent()){
				empresaRepo.getSucursales().add(sucursalRepo);
				empresaRepo.getSucursales().add(sucursalRepo2.get());
				empresaRepository.save(empresaRepo);
				sucursalRepo.setEmpresa(empresaRepo);
				sucursalRepo2.get().setEmpresa(empresaRepo);
				sucursalRepo3.getCategorias().add(categoriaRep);
				sucursalRepository.save(sucursalRepo);
				sucursalRepository.save(sucursalRepo2.get());
				sucursalRepository.save(sucursalRepo3);
				categoriaRep.getSucursales().add(sucursalRepo);
				categoriaRep.getSucursales().add(sucursalRepo2.get());
				var empleado1 = empleadoRepository.findById(2L);
				if(empleado1.isPresent()){
					sucursalRepo.getEmpleados().add(empleado1.get());

					sucursalRepository.save(sucursalRepo);
				}
			}

			logger.info("------------Nombre de sucursales de la empresa id 2------------");
			empresaRepo.getSucursales()
					.stream()
					.map(Sucursal::getNombre)
					.forEach(logger::info);

			logger.info("------------Nombre de empresa de la sucursal id 3------------");
			logger.info("{}",sucursalRepo.getEmpresa().getNombre());
			logger.info("------------Empleados de la sucursal id 3------------");
			sucursalRepo.getEmpleados().stream()
					.map(Empleado::getNombre)
					.forEach(logger::info);

			logger.info("------------Nombre de empresa de la sucursal id 4------------");
			logger.info("{}",sucursalRepo2.get().getEmpresa().getNombre());

			logger.info("----------------Sucursal Lujan ---------------------");
			logger.info("{}",sucursalLujan);
			logger.info("----------------Sucursal San Martin ---------------------");
			logger.info("{}",sucursalSanMartin);
			logger.info("----------------Pedido ---------------------");
			logger.info("{}",pedido);
		};
	}

}



