#GLOBAL EVENT PROCESSOR (GEP)

Global Event Processor(GEP) is responsible to consume the Usage & System events and transform them into Discrete Events that will be further consume by the Data Analytics Pipeline.

(Study Player) Usage Events currently sourced from the Log Writer which will be transformed by the GEP are listed below:

{"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","activityTime":1476771009164,"eventId":"df39e006-310b-4e0f-830a-95a213cfd6e9","eventName":"collection.performance","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30","collectionType":"assessment","context":{"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","pathId":0,"sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","questionCount":10,"partnerId":null,"tenantId":null},"result":{"score":90.0,"maxScore":10.0,"timeSpent":6488050,"reaction":0}}

{"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","activityTime":1476770688465,"eventId":"7c3e29ed-d766-4a0b-83ec-89ce6c941b13","eventName":"resource.performance","resourceId":"b4116300-6ed6-4705-b5a2-9fe61fe9abba","resourceType":"question","context":{"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30","collectionType":"assessment","pathId":0,"sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","partnerId":null,"tenantId":null},"result":{"score":100.0,"maxScore":1.0,"timeSpent":66284,"answerStatus":"correct"}}


{"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","activityTime":1476770382736,"eventId":"df39e006-310b-4e0f-830a-95a213cfd6e9","eventName":"collection.start","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30","collectionType":"assessment","context":{"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","pathId":0,"sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","questionCount":10,"partnerId":null,"tenantId":null},"result":{"score":null,"maxScore":null,"timeSpent":0.0}}


Discrete Event naming convention
Usage DE

All the usage DE will be named as:

usage.[object].[metrics]

The value for object would be what user has interacted with e.g. resource or collection. 
Metrics would have values like timespent, score, reaction etc.

System DE

[entity].[action]
The entity could be class, user etc. The action could be updated, deleted etc.

Note that a single event would be catering to a single metrics. We are not going to overload events with multiple metrices.

GEP will publish the Discrete Events on its corresponding Kafka Topics. 

Kafka topic naming convention

All Kafka topics will be named as:

org.gooru.da.sink.[event source].[event bucket]

"da" stands for Data Analytics.

Here event source could be one of following:
    "gep": In case event is coming directly from gep component
    "dep": In case event is generated as part of processing by dep component


Event bucket could either simply be the event name (most probably in cases of usage demux generated events, after removing string "usage") or they could be entity in case of System DE.
DE emitted by demux

Following are a few samples of Discrete Events
    usage.resource.timspent
    usage.question.timespent
    usage.collection.score
    usage.assessment.score
    usage.collection.timespent
    usage.assessment.timespent



Kafka Topics (The list will keep growing)

org.gooru.da.sink.gep.assessment.score
org.gooru.da.sink.gep.collection.score
org.gooru.da.sink.gep.collection.timespent
org.gooru.da.sink.gep.collection.start
org.gooru.da.sink.gep.resource.timespent
org.gooru.da.sink.gep.question.timespent
org.gooru.da.sink.gep.question.score
org.gooru.da.sink.logW.usage.events - Used as a communication medium between Log Writer and GEP.


Sample Discrete Events Sourced by GEP (The list will keep growing)

{"result":{"timeSpent":35035},"eventId":"979044d0-2b73-4814-b4ae-7a965b73ecf3","resourceId":"c558cb6a-9757-461c-9b3d-25a49e0c5e6d","context":{"collectionType":"collection","classId":"73294a4d-8825-4106-8cc9-8c26f370d06c","unitId":"4af505ff-9fc0-49c9-b764-d315b2df9be6","lessonId":"fa9f52d6-4f15-4f7e-a13c-25dd5b114889","sessionId":"2018cab8-34e6-45e8-9890-910d9f8e19ba","courseId":"987e0144-631d-4bac-a82b-26ffbf5257a0","collectionId":"08d7462f-6fd3-4fce-b881-b9e63f194a5e"},"activityTime":1476766477963,"eventName":"usage.resource.timespent","userId":"409e31a6-a989-4aff-b6a5-7222542863a4","resourceType":"resource"}

{"result":{"timeSpent":82863},"eventId":"f87d67fb-ba28-4494-b270-cc867ad5ff72","resourceId":"10ca0d81-60dd-47f2-ac5b-6259a54a59b0","context":{"collectionType":"collection","classId":"73294a4d-8825-4106-8cc9-8c26f370d06c","unitId":"4af505ff-9fc0-49c9-b764-d315b2df9be6","lessonId":"fa9f52d6-4f15-4f7e-a13c-25dd5b114889","sessionId":"2018cab8-34e6-45e8-9890-910d9f8e19ba","courseId":"987e0144-631d-4bac-a82b-26ffbf5257a0","collectionId":"08d7462f-6fd3-4fce-b881-b9e63f194a5e"},"activityTime":1476766561135,"eventName":"usage.question.timespent","userId":"409e31a6-a989-4aff-b6a5-7222542863a4","resourceType":"question"}


{"result":{"score":100},"eventId":"bb1d65e7-5abc-4fb7-8384-d447331f6a1d","resourceId":"241ff3f4-099d-4801-9ae8-fc5a44c866bd","context":{"collectionType":"assessment","classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30"},"activityTime":1476770510393,"eventName":"usage.question.score","userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","resourceType":"question"}

{"eventId":"df39e006-310b-4e0f-830a-95a213cfd6e9","collectionType":"assessment","context":{"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","pathId":0,"courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91"},"eventName":"usage.collection.start","activityTime":1476770382736,"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30"}

{"result":{"score":100},"eventId":"33edea96-1ff6-43bf-9a2b-7c72f0f5e99f","collectionType":"collection","context":{"questionCount":7,"classId":"73294a4d-8825-4106-8cc9-8c26f370d06c","unitId":"4af505ff-9fc0-49c9-b764-d315b2df9be6","lessonId":"fa9f52d6-4f15-4f7e-a13c-25dd5b114889","sessionId":"2018cab8-34e6-45e8-9890-910d9f8e19ba","pathId":0,"courseId":"987e0144-631d-4bac-a82b-26ffbf5257a0"},"eventName":"usage.collection.score","activityTime":1476767084467,"userId":"409e31a6-a989-4aff-b6a5-7222542863a4","collectionId":"08d7462f-6fd3-4fce-b881-b9e63f194a5e"}


{"result":{"score":90},"eventId":"df39e006-310b-4e0f-830a-95a213cfd6e9","collectionType":"assessment","context":{"questionCount":10,"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","pathId":0,"courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91"},"eventName":"usage.assessment.score","activityTime":1476771009164,"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30"}

{"result":{"timeSpent":4579800},"eventId":"df39e006-310b-4e0f-830a-95a213cfd6e9","collectionType":"assessment","context":{"questionCount":10,"classId":"8c256e4a-4b37-423c-82b6-82fd8a128af2","unitId":"9ee215dc-5137-43c0-9808-d420e565f81a","lessonId":"ecfb3bd6-5aa7-4210-8df6-e9430ced5a3f","sessionId":"5e5fdb34-c202-4d34-827b-12aef4Nov069","pathId":0,"courseId":"566c8fcd-2dda-4f6d-a6e1-e4f2dbb8bb91"},"eventName":"usage.collection.timespent","activityTime":1476771009164,"userId":"M1k344e1-631e-4642-875d-8b07a5e3b421","collectionId":"5e5fdb34-c202-4d34-827b-12aef4JuLy30"}

