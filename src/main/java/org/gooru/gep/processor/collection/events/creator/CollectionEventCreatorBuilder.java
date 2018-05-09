package org.gooru.gep.processor.collection.events.creator;

import org.gooru.gep.processor.collection.events.parser.CollectionEventObject;


/**
 * @author mukul@gooru
 *  
 */
public class CollectionEventCreatorBuilder {
	
    private CollectionEventCreatorBuilder() {
        throw new AssertionError();
    }

    public static CollectionEventCreator buildCollectionScoreEventCreator(CollectionEventObject collEvent) {
        return new CollectionScoreEventCreator(collEvent);
    }

    public static CollectionEventCreator buildAssessmentScoreEventCreator(CollectionEventObject collEvent) {
        return new AssessmentScoreEventCreator(collEvent);
    }

    public static CollectionEventCreator buildCollectionTimespentEventCreator(CollectionEventObject collEvent) {
        return new CollectionTimespentEventCreator(collEvent);
    }    
}
