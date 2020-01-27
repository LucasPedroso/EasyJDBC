package com.github.tadeuespindolapalermo.easyjdbc.operations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.github.tadeuespindolapalermo.easyjdbc.connection.InfoConnection;
import com.github.tadeuespindolapalermo.easyjdbc.connection.SingletonConnection;
import com.github.tadeuespindolapalermo.easyjdbc.entity.Entity;
import com.github.tadeuespindolapalermo.easyjdbc.entity.EntityNamed;
import com.github.tadeuespindolapalermo.easyjdbc.entity.EntityUniqueAttribute;
import com.github.tadeuespindolapalermo.easyjdbc.enumeration.EnumDatabase;
import com.github.tadeuespindolapalermo.easyjdbc.operations.OperationsInterfaceRepositoryImpl;
import com.github.tadeuespindolapalermo.easyjdbc.util.Utils;

public class OperationsInterfaceRepositoryImplTest {
	
	private static final String[] COLUMNS = {"id", "name", "lastname", "cpf", "weight", "approved", "age"};
	
	private static final String ENTITY = "entity";
	
	private static final String CPF_MOCK = "12121212121";	
	
	private static final String DESCRIPTION_MOCK = "Mock Description";

	static {
		connectionTest();
	}	

	private static void connectionTest() {
		InfoConnection.setDatabase(EnumDatabase.POSTGRE);
		InfoConnection.setNameDatabase("easyjdbc");
		InfoConnection.setPassword("postgres1985");
		InfoConnection.setUser("postgres");
		InfoConnection.setHost("127.0.0.1");
		InfoConnection.setPort("5432");
		SingletonConnection.getConnection();
	}
	
	@Test
	public void savePersistenceEntityUniqueAttribute() throws Exception {
		EntityUniqueAttribute e = createEntityUniqueAttribute();
		OperationsInterfaceRepositoryImpl<EntityUniqueAttribute> p = new OperationsInterfaceRepositoryImpl<>(EntityUniqueAttribute.class);
		assertNotNull(p.save(e));
	}

	@Test
	public void savePersistenceClassTestA() throws Exception {
		Entity e = createEntityInsert();
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
		assertNotNull(p.save(e));
	}
	
	@Test
	public void savePersistenceClassNamedTest() throws Exception {
		EntityNamed e = createEntityNamedInsert();
		OperationsInterfaceRepositoryImpl<EntityNamed> p = new OperationsInterfaceRepositoryImpl<>(EntityNamed.class);
		assertNotNull(p.save(e));
	}
	
	@Test
	public void savePersistenceClassTestB() throws Exception {
		Entity e = createEntityInsert();
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
		assertNotNull(p.save(e, ENTITY));
	}
	
	@Test
	public void savePersistenceClassTestC() throws Exception {
		Entity e = createEntityInsert();
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);		
		assertNotNull(p.save(e, COLUMNS));
	}
	
	@Test
	public void savePersistenceClassTestD() throws Exception {
		Entity e = createEntityInsert();
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
		assertNotNull(p.save(e, ENTITY, COLUMNS));
	}
	
	@Test
    public void deleteTest() throws Exception {
        OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
        List<Entity> entities = p.getAll();
        if (entities.size() == 0 ||entities.isEmpty()) {
        	Entity e = createEntityInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
        boolean deleted = false;
        for (int i = 0; i <= entities.size(); i++) {
        	if (deleted)
        		break;
        	for (Entity entity : entities) {
        		deleted = p.delete(Entity.class, entity.getId());
        		break;
			}
        }        
        assertTrue(deleted);
    }
	
	@Test
    public void deleteEntityNamedTest() throws Exception {
        OperationsInterfaceRepositoryImpl<EntityNamed> p = new OperationsInterfaceRepositoryImpl<>(EntityNamed.class);
        List<EntityNamed> entities = p.getAll();
        if (entities.size() == 0 ||entities.isEmpty()) {
        	EntityNamed e = createEntityNamedInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
        boolean deleted = false;
        for (int i = 0; i <= entities.size(); i++) {
        	if (deleted)
        		break;
        	for (EntityNamed entity : entities) {
        		deleted = p.delete(EntityNamed.class, entity.getNumber());
        		break;
			}
        }        
        assertTrue(deleted);
    }
	
	@Test
	public void updateIdTest() throws Exception {
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);		
		List<Entity> entities = p.getAll();
		Entity entityAssert = null;
        if (entities.size() == 0 ||entities.isEmpty()) {
        	Entity e = createEntityInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
		for (Entity entity : entities) {
			entityAssert = createEntityUpdate();
			entityAssert = p.update(entityAssert, entity.getId());
			break;
		}
		assertNotNull(entityAssert);
	}
	
	@Test
	public void updateEntityTest() throws Exception {
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);		
		List<Entity> entities = p.getAll();
		Entity entityAssert = null;
        if (entities.size() == 0 ||entities.isEmpty()) {
        	Entity e = createEntityInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
		for (Entity entity : entities) {
			entityAssert = p.searchById(entity.getId());
			entityAssert = createEntityUpdate(entityAssert);
			entityAssert = p.update(entityAssert);
			break;
		}
		assertNotNull(entityAssert);
	}
	
	@Test
	public void seacrhByIdAndUpdateEntityTest() throws Exception {		
		OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
		List<Entity> entities = p.getAll();
        long idSearch = 0;
        if (entities.size() == 0 ||entities.isEmpty()) {
        	Entity e = createEntityInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
        for (Entity entity : entities) {
			idSearch = entity.getId();
			break;
		}
        Entity e = p.searchById(idSearch);
		e.setCpf(CPF_MOCK);
		assertNotNull(p.update(e));
	}
	
	@Test
	public void seacrhByIdAndUpdateEntityClassNamedTest() throws Exception {		
		OperationsInterfaceRepositoryImpl<EntityNamed> p = new OperationsInterfaceRepositoryImpl<>(EntityNamed.class);		
		List<EntityNamed> entities = p.getAll();
		String idSearch = "";        
        if (entities.size() == 0 ||entities.isEmpty()) {
        	EntityNamed e = createEntityNamedInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
        for (EntityNamed entity : entities) {
			idSearch = entity.getNumber();
			break;
		}
        EntityNamed e = p.searchById(idSearch);		
		e.setDescription(DESCRIPTION_MOCK);
		assertNotNull(p.update(e));
	}	
	
	@Test
    public void getAllTest() throws Exception {
        OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
        Utils.print(p.getAll());
        assertNotNull(p.getAll());
    }
	
	@Test
    public void searchByIdTest() throws Exception {
        OperationsInterfaceRepositoryImpl<Entity> p = new OperationsInterfaceRepositoryImpl<>(Entity.class);
        List<Entity> entities = p.getAll();
        long idSearch = 0;
        if (entities.size() == 0 ||entities.isEmpty()) {
        	Entity e = createEntityInsert();    		
    		p.save(e);
    		entities = p.getAll();
        }
        for (Entity entity : entities) {
			idSearch = entity.getId();
			break;
		}
        Utils.print(p.searchById(idSearch));
        assertNotNull(p.searchById(idSearch));
    }
	
	private Entity createEntityInsert() {
		Entity e = new Entity();
		e.setAge(55);
		e.setApproved(true);
		e.setCpf("90253056012");		
		e.setLastname("Espíndola Palermo");
		e.setName("Tadeu");
		e.setWeight(82D);
		return e;
	}
	
	private EntityUniqueAttribute createEntityUniqueAttribute() {
		EntityUniqueAttribute e = new EntityUniqueAttribute();
		e.setCanceled(true);
		return e;
	}	
	
	private EntityNamed createEntityNamedInsert() {
		EntityNamed e = new EntityNamed();
		Integer number = new Random().nextInt(999999999);		
		e.setDescription("Test");
		e.setNumber(number.toString());
		return e;
	}
	
	private Entity createEntityUpdate() {
		Entity e = new Entity();
		e.setAge(55);
		e.setApproved(false);
		e.setCpf("17731193039");		
		e.setLastname("BBBBBBBBBBBBBB");
		e.setName("Osvaldo");
		e.setWeight(105D);
		return e;
	}
	
	private Entity createEntityUpdate(Entity e) {		
		e.setAge(55);
		e.setApproved(false);
		e.setCpf("17731193039");		
		e.setLastname("ANAANAANAANAANAANAANA");
		e.setName("Osvaldooooooooo");
		e.setWeight(199D);
		return e;
	}

}