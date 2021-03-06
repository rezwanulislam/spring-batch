/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.batch.item.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
//
//import org.springframework.data.neo4j.conversion.ResultConverter;

/**
 * <p>
 * Extensions of the {@link AbstractNeo4jItemReader}.
 * </p>
 *
 * @author Michael Minella
 */
public class Neo4jItemReader<T> extends AbstractNeo4jItemReader {

	@Override
	protected Iterator<T> doPageRead() {
		SessionFactory factory = getSessionFactory();

		Iterable<T> queryResults;

		if(factory != null) {
			Session session = factory.openSession();

			queryResults = session.query(getTargetType(),
					generateLimitCypherQuery(),
					getParameterValues());
		}
		else {
			queryResults = getTemplate().queryForObjects(
					getTargetType(), generateLimitCypherQuery(), getParameterValues());

		}

		if(queryResults != null) {
			return queryResults.iterator();
		}
		else {
			return new ArrayList<T>().iterator();
		}
	}
}
