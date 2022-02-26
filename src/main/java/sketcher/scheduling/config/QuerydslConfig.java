package sketcher.scheduling.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() { // 어느 곳에서나 JPAQueryFactory 주입 받아 Querydsl 사용 가능
        return new JPAQueryFactory(entityManager);
    }
}