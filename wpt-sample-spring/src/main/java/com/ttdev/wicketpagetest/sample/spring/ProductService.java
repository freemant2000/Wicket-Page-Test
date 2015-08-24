package com.ttdev.wicketpagetest.sample.spring;

import java.util.List;

public interface ProductService {
  
	List<Product> getAll();

	void add(Product p);

	void delete(Product p);

}
