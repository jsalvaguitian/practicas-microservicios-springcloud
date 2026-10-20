package com.jesi.springcloud.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jesi.springcloud.msvc.items.models.ProductDTO;
/*
Importante:
1-Habilitar los feign cliente en ItemsApplication.
2- Colocar la url correcta del microservicio Producto y misma
ruta de mapeo (si el microservicio Product esta usando requestmapping) y los parametros de los 
metodos si los tienen asi nos podemos conectar con el microservicio de producto que esta funcionando 
como nuestro repositorio.
3-Los nombres de los metodos y tipo de dato de retorno no es necesario
que sea lo mismo como en el microservicio de producto
*/

@FeignClient(url = "localhost:8001", name = "msvc-product") //esto no seria necesario si usaramos el Servidor de nombre Eureka
public interface ProductFeignClientHttp {

    @GetMapping("/api/products")
    List<ProductDTO> findAll();

    @GetMapping("/api/products/{id}")
    ProductDTO details(@PathVariable long id);


    
} 