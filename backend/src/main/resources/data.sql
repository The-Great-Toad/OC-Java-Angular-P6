-- Create test topics
INSERT INTO topics (name, description) VALUES
('Java', 'Discussions about Java programming language, JVM, and ecosystem'),
('JavaScript', 'JavaScript, TypeScript, and modern web development'),
('Spring Boot', 'Spring Boot framework, microservices, and enterprise Java'),
('Angular', 'Angular framework, RxJS, and single-page applications'),
('React', 'React library, hooks, and component-based architecture'),
('DevOps', 'CI/CD, containerization, orchestration, and deployment strategies'),
('Database', 'SQL, NoSQL, database design, and optimization techniques'),
('Cloud', 'Cloud platforms, serverless architecture, and cloud-native development'),
('Security', 'Application security, authentication, authorization, and best practices'),
('Testing', 'Unit testing, integration testing, TDD, and quality assurance');

-- Create test users (password: "password123")
INSERT INTO users (id, name, email, password, created_at, updated_at) VALUES
(UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 'Alice Dupont', 'alice@test.com', '$2a$10$Zu5FT05HaUaSXYt907ranuI4F.nYVIfNfdmV1N//lUlDxKDfNuT9y', NOW(), NOW()),
(UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440001', '-', '')), 'Bob Martin', 'bob@test.com', '$2a$10$Zu5FT05HaUaSXYt907ranuI4F.nYVIfNfdmV1N//lUlDxKDfNuT9y', NOW(), NOW()),
(UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440002', '-', '')), 'Charlie Dubois', 'charlie@test.com', '$2a$10$Zu5FT05HaUaSXYt907ranuI4F.nYVIfNfdmV1N//lUlDxKDfNuT9y', NOW(), NOW());

-- Create test posts
INSERT INTO posts (title, content, author_id, topic_id, created_at) VALUES
('Les nouveautés de Java 21', 'Java 21 apporte plusieurs améliorations majeures, notamment les record patterns et les pattern matching améliorés. Ces fonctionnalités permettent d''écrire du code plus concis et expressif. Les virtual threads sont également une révolution pour la concurrence en Java.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),

('Introduction à Spring Boot 3', 'Spring Boot 3 nécessite Java 17 minimum et apporte le support complet de Jakarta EE 10. La migration depuis Spring Boot 2 peut nécessiter quelques ajustements, notamment au niveau des imports javax vers jakarta. Cet article explore les principales étapes de migration.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440001', '-', '')), 3, DATE_SUB(NOW(), INTERVAL 4 DAY)),

('Angular Signals : Le futur de la réactivité', 'Angular 17 introduit les Signals comme nouveau système de réactivité. Cette approche permet une gestion plus fine des changements et améliore considérablement les performances. Voyons comment migrer progressivement de RxJS vers les Signals.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440002', '-', '')), 4, DATE_SUB(NOW(), INTERVAL 3 DAY)),

('Optimisation des requêtes SQL', 'L''optimisation des requêtes SQL est cruciale pour les performances d''une application. Cet article présente les bonnes pratiques : utilisation des index, éviter les SELECT *, optimisation des JOIN, et analyse des plans d''exécution. Des exemples concrets sont fournis pour chaque cas.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 7, DATE_SUB(NOW(), INTERVAL 2 DAY)),

('DevOps : CI/CD avec GitHub Actions', 'GitHub Actions facilite la mise en place de pipelines CI/CD. Cet article détaille comment créer un workflow pour tester, builder et déployer une application Spring Boot sur Azure. Les bonnes pratiques de sécurité pour la gestion des secrets sont également abordées.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440001', '-', '')), 6, DATE_SUB(NOW(), INTERVAL 1 DAY)),

('React Hooks : useState et useEffect', 'Les hooks React ont révolutionné la façon d''écrire des composants. useState permet de gérer l''état local tandis que useEffect gère les effets de bord. Cet article explore les patterns courants et les pièges à éviter lors de l''utilisation de ces hooks fondamentaux.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440002', '-', '')), 5, DATE_SUB(NOW(), INTERVAL 12 HOUR)),

('Sécurité des applications web : OWASP Top 10', 'La sécurité des applications web est primordiale. L''OWASP Top 10 recense les vulnérabilités les plus critiques : injection SQL, XSS, CSRF, etc. Cet article détaille chaque vulnérabilité et présente les contre-mesures à mettre en place avec Spring Security.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 9, DATE_SUB(NOW(), INTERVAL 6 HOUR)),

('Déploiement sur AWS : Guide complet', 'Amazon Web Services offre de nombreux services pour héberger vos applications. Ce guide couvre EC2, RDS, S3, et CloudFront. Nous verrons comment déployer une application full-stack avec une base de données MySQL et un frontend React.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440001', '-', '')), 8, DATE_SUB(NOW(), INTERVAL 3 HOUR)),

('Tests unitaires avec JUnit 5 et Mockito', 'Les tests unitaires garantissent la qualité du code. JUnit 5 apporte de nombreuses améliorations : @ParameterizedTest, @Nested, assertions améliorées. Mockito permet de mocker les dépendances facilement. Cet article présente les bonnes pratiques de testing en Java.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440002', '-', '')), 10, DATE_SUB(NOW(), INTERVAL 1 HOUR)),

('JavaScript moderne : ES2024 features', 'ECMAScript 2024 introduit de nouvelles fonctionnalités intéressantes. Au programme : les decorators, les pattern matching, et les améliorations des arrays. Cet article explore ces nouveautés avec des exemples pratiques et des cas d''usage réels.', 
 UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 2, DATE_SUB(NOW(), INTERVAL 30 MINUTE));
