
ORM:- Object relation mapping framework
	Object hierarchy map to table relational hierarchy
	Java object as representation of relational database
	Mapping between plain POJO with db tables
	

Problems:-
	1) Too many SQL statements
	2) Too many copy code
	3) Manually handled association
	4) Database dependent
	
Solution
	ORM Techniques
	Java objcest as representation of relational database
	Mapping between plain POJO with db tables
	xml or annotations
	Hides complexity of SQL and jdbc
	
	
SessionFactory should be per datasource

Session <-- SessionFactory <-- Configutation

Configuation :- Hibernate config file at root of classpath
			DB config + Dialets ( convert application queries to native db queries)
			
Hibernate 4 uses Configuration object with hibernate.cfg.xml for SessionFactory
Hibernate 5 uses StandardServiceRegistryBuilder and MetaDataSources with hhibernate.cfg.xml for session factory

Hibernate 4 for Id column GenerateValues strategy uses AUTO
Hibernate 5 for Id column GenerateValues strategy uses IDENTITY

Entity and value type
A component which has no individual identity 
for eg Person Table has attr likes id name, birthdate, street no, city, zip
We can have two components here one is Person and other is Address (street, city, zip) and address dont have own identity
We can have 2 classes for 1 table one is Person (id, name, birthdate) Entity type and value type Address (street, city, zip)

@Entity
class Person{

@id
int id

@Embedded
Adress address
}

@Embeddable
class Adress{
street, city etc
}

Cascade Types
Entity relationships often depend on the existence of another entity — 
for example, the Person–Address relationship. 
Without the Person, the Address entity doesn't have any meaning of its own. 
When we delete the Person entity, our Address entity should also get deleted.

Cascading is the way to achieve this.
 When we perform some action on the target entity, 
 the same action will be applied to the associated entity.
 
 Persist, remove, ALL, merge,refresh,detach
 
Relation Ships
ManytoOne: - Student has guide. Guide can guide many students
class Student{

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="guide_id")
	private Guide guide;
	
}

class Guide{
-- No mapping only fields of guides
	
}

-- Its unidirectional mapping from Student to Guide

-- To make bidirectional 
oneToMany
class Guide{
	@OneToMany(mappedBy="guide", cascade={CascadeType.PERSIST})
	private Set<Student> students = new HashSet<Student>();		
}

Relationship is updated by owner only. other entity is inverse end. here Student is owner entity responsilble for the relation updation


ManyToMany relation (should have join table)
Movie has many actor and actor can work for many movies.. Here is Movies is owner entity and actor is the inverse end 
owner is repsonsible for relationship updation

@Entity
public class Movie {
	@ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(
            name="movie_actor",
            joinColumns={@JoinColumn(name="movie_id")},
            inverseJoinColumns={@JoinColumn(name="actor_id")}
    	)	
	private Set<Actor> actors = new HashSet<Actor>();	
	
}

@Entity
public class Actor {
	@ManyToMany(mappedBy="actors")
	private Set<Movie> movies = new HashSet<Movie>();
}
	

composite Key 
more than one keys to identify as unique record
@EmbededId is used to have the same

@Embeddable
public class ParentPrimaryKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstname;
	private String lastname;
}

@Entity
public class Parent {
	
	@EmbeddedId
	private ParentPrimaryKey parentPrimaryKey;
}

First Level Caching
 first level caching is at session or entity manager level. object is cached there 
 It will improve the performance if someone tries to retrive the same object in same session


Polymorphism and inheritance

Single Table:- Table per hierarchy discriminatorcolumn and values are need to specify 
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
//@Inheritance(strategy=InheritanceType.JOINED) // to be used when you want to test JOINED strategy for inheritance mapping
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) // to be used when you want to test TABLE_PER_CLASS (Table per concrete class) strategy for inheritance mapping
public abstract class Animal {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue(strategy=GenerationType.TABLE) // to be used when using TABLE_PER_CLASS strategy
	private Long id;
}

@Entity
public class Dog extends Animal {

	@Override
	public String makeNoise() {
		return "woof woof..";
	}

}

@Entity
public class Cat extends Animal {
	
	
	//@Column(nullable=false) // cannot be used when using SINGLE_TABLE strategy
	private int size;	
		
	
	
	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	@Override
	public String makeNoise() {
		return "meow meow..";
	}
	
}


Joined
@Inheritance(strategy = InheritanceType.JOINED) 
tables per object hierarchy . in above scneario 3 tables Animal cat and Dog will be present. Cat and Dog will have FK on animal table

Query fetching done through the joins


Table per Concrete 
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  or table per concreate classs
In above example 2 concrete classes are created for Dog and Cat and for the parent one.. Parent will have auto generattion Type = Table 
it will create hibernate_sequence to have id values of columns in subclass 

query fetching is done through select aa.* from (select * from Dog union select * from Cat) aa

Locking

When 2 users concurrently working on same object in different session we may get unexpected result to avoid that
we use locking mechnism
1) Pessimistic  Locking:-
	Its db level locking mechanism where required rows are locked to being modified while working on the those objects
	Lock Modes
	PESSIMISTIC_READ – allows us to obtain a shared lock and prevent the data from being updated or deleted
	PESSIMISTIC_WRITE – allows us to obtain an exclusive lock and prevent the data from being read, updated or deleted
	PESSIMISTIC_FORCE_INCREMENT – works like PESSIMISTIC_WRITE and it additionally increments a version attribute of a versioned entity
	e.g:-
	Query query = entityManager.createQuery("from Student where studentId = :studentId");
	query.setParameter("studentId", studentId);
	query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
	query.getResultList()
2) Optimistic Locking
	 optimistic locking is based on detecting changes on entities by checking their version attribute. 
	If any concurrent update takes place, OptmisticLockException occurs. After that, we can retry updating the data.
	
Isolation Levels:-
	Rules
		Defines extend to which transaction is visible to other
		How and when changes made by one transaction be visible to other

SERIALIZABLE:-  This is the Highest isolation level. True isolation hence performance is slow
Transaction executed serially one after other 
connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
@Transactional(isolation = Isolation.REPEATABLE_READ)

Repeatable Read (Phantom reads)
Lets say transaction T1 reads the some data using some criteria and meanwhile Transaction T2 inserted some data matching T1 criteria
if We re-execute the criteria again from T1 we will get more rows (newly added by T2 called as phantom) than earlier

It works with insert and delete stuff	


Read Committed: 
Commited data will be available to other trans as soon as it commited
Lets say T1 is reading with some criteria and it got some result and T2 commited some data with few insert and modification
If t1 again tries to read the data with same criteria then it will get both inserted and modified changes

It works with Insert, delete and update stuff

Read Uncommited:-  Lowest isolation level
 Read Uncommitted is the lowest isolation level. In this level, one transaction may read not yet committed changes made by other transaction, 
 thereby allowing dirty reads. In this level, transactions are not isolated from each other.
 
Default isolation level mysql:- reapeatable read oracle have read commited

Second level cache
Hibernate second level cache uses a common cache for all the session object of a session factory (or entitymanagerFactory). 

It is useful if you have multiple session objects from a session factory.

SessionFactory holds the second level cache data. It is global for all the session objects and not enabled by default.

EHCache, Joboss cache are the provider of 2nd level cache
Below are some strategies for 2nd level cache

By default catergory is read write stategy

read-only: caching will work for read only operation. Data never will be modified like country codes
nonstrict-read-write: caching will work for read and write but one at a time. its being used when data is hardly modified
read-write: caching will work for read and write, can be used simultaneously.(Its similar to read committed isolation level)
transactional: caching will work for transaction. its like repeatable read mostly used cluster JVM

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Student {

}
Cache is divided into different regions Query region, Entity region, Collection region

for ehcache configurartion comes in ehcache.xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
				  xsi:noNamespaceSchemaLocation="ehcache.xsd">

	 
	<cache name="entity.Guide"
				  maxElementsInMemory="1000"
				  eternal="true"
				  overflowToDisk="false"
	 />
</ehcache>
