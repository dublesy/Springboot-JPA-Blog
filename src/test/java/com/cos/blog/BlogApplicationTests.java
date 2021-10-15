package com.cos.blog;

import com.cos.blog.model.Dish;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@SpringBootTest
@Slf4j
class BlogApplicationTests {


	@Autowired
	private UserRepository userRepository;

	List<Dish> menu;


//	public BlogApplicationTests(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}

	@Test
	void contextLoads() {

	}


	@Test
	void usersTest(){
		List<User> users = userRepository.findAll();

		//users.stream().filter(user -> user.)

		List<String> threeHighCaloricDishNames = menu.stream()
				.filter(dish -> dish.getCalories() > 200)
				.map(Dish::getName)
				.limit(3)
				.collect(toList());
		System.out.println(threeHighCaloricDishNames);

		List<Dish> collect1 = menu.stream().filter(dish -> dish.isVegetarian()).collect(toList());

		System.out.println("collect1 >>> "+ collect1);


		List<String> collect = menu.stream().map(Dish::getName).collect(toList());

		System.out.println("collect>>> "+collect);

		users.forEach(System.out::println);
		log.info("users = {}", users);
		Assertions.assertThat(users).isNotEmpty();
	}

	@PostConstruct
	void initObject(){
		menu = Arrays.asList(
				new Dish("pork",false, 800, Dish.Type.MEAT),
				new Dish("beef",false, 700, Dish.Type.MEAT),
				new Dish("chicken",false, 400, Dish.Type.MEAT),
				new Dish("french",true, 530, Dish.Type.OTHER),
				new Dish("rice",true, 120, Dish.Type.OTHER),
				new Dish("season",true, 550, Dish.Type.OTHER),
				new Dish("pizza",true, 300, Dish.Type.OTHER),
				new Dish("prawns",false, 450, Dish.Type.FISH),
				new Dish("salmon",false, 700, Dish.Type.FISH)
		) ;
	}
}
