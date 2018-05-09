package org.gooru.gep.processor.resource.events.creator;

import org.gooru.gep.processor.resource.events.parser.ResourceEventObject;

/**
 * @author mukul@gooru
 *  
 */
public class ResourceEventCreatorBuilder {
	
    private ResourceEventCreatorBuilder() {
        throw new AssertionError();
    }

    public static ResourceEventCreator buildResourceTimespentEventCreator(ResourceEventObject rtEvent) {
        return new ResourceTimespentEventCreator(rtEvent);
    }
    
    public static ResourceEventCreator buildQuestionTimespentEventCreator(ResourceEventObject rtEvent) {
        return new QuestionTimespentEventCreator(rtEvent);
    }

    public static ResourceEventCreator buildQuestionScoreEventCreator(ResourceEventObject rtEvent) {
        return new QuestionScoreEventCreator(rtEvent);
    }

}
