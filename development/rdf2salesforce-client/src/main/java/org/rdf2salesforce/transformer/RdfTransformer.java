package org.rdf2salesforce.transformer;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;

public class RdfTransformer {
	
	// just some testing
	public static void transform(){
		ValueFactoryImpl factory = new ValueFactoryImpl();
		URI bob = factory.createURI("http://example.org/bob");
		URI name = factory.createURI("http://example.org/name");
		Literal bobsName = factory.createLiteral("Bob");
	//	Statement nameStatement = factory.createStatement(bob, name, bobsName);
		
	}

}
