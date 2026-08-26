package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);

        //converte o page de Product para um page de ProductDTO
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {

        //instancia um product
        Product entity = new Product();

        //copia o dto recebido para o entity
        copyDtoToEntity(dto, entity);

        //salva no banco e retorna o objeto salvo (product salvo)
        entity = repository.save(entity);

        //retorna o dto passando a entidade para ser convertida em dto
        return new ProductDTO(entity);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        //instancia o entity monitorado pela JPA
        Product entity = repository.getReferenceById(id);

        //copia o dto para a entidade
        copyDtoToEntity(dto, entity);

        //salva no banco e retorna o objeto salvo (product salvo)
        entity = repository.save(entity);

        //retorna o dto passando a entidade para ser convertida em dto
        return new ProductDTO(entity);

    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {

        //copia os dados do dto para o product
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
