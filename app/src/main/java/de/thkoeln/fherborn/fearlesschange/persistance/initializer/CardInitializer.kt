package de.thkoeln.fherborn.fearlesschange.persistance.initializer

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

class CardInitializer : DataInitializer<Card>("card") {
    override fun getItemValues(item: Card?) = hashMapOf(
            "id" to item?.id,
            "title" to item?.title,
            "pictureName" to item?.pictureName,
            "problem" to item?.problem,
            "buts" to item?.buts,
            "solution" to item?.solution,
            "favorite" to item?.favorite
    )

    override fun getItems(): List<Card> = listOf(
            Card(
                    0,
                    "Accentuate the Positive",
                    "", //Empty if not exist
                    "Your attempts to scare others are not working.",
                    "Misguided optimisim can promote artificial happiness and discourage critical reflection(Mentor), Take responsibility for adressing the problems(Concrete Action Plan)", //Empty if not exists
                    "Inspire people throughout the change initiative with a sense of optimism rather than fear."
            ),
            Card(
                    1,
                    "Concrete Action Plan",
                    "",
                    "Leading a change initiative, with its many twists and turns and ever-growing list of things to do, can make you feel out of control.",
                    "Take time for relection about what you can do diffrently(Time for Reflection), If you 'fall of the wagon', look for a shoulder to cry on to help you and your team(Shoulder to Cry On)",
                    "Describe the next small step for reaching a milestone goal in terms of concrete actions that include what you will do, where, and when."
            ),
            Card(
                    2,
                    "Easier Path",
                    "",
                    "What can you do to make it easier for people to change?",
                    "When novelty wears off, take baby steps to tackle another obstalce(Baby Steps)",
                    "Change the environment in a way that will encourage people to adopt the new idea."
            ),
            Card(
                    3,
                    "Elevator Pitch",
                    "", //Empty if not exist
                    "When you have a chance to introduce someone to your idea, you don’t want to stumble around for the right words to say.",
                    "Stay in touch and communicate additional information with persistant PR(Stay in Touch)", //Empty if not exists
                    "Craft a couple of sentences that contain your key message."
            ),
            Card(
                    4,
                    "Emotional Connection",
                    "", //Empty if not exist
                    "As you share information about your new idea, you might believe that logical argument is enough to persuade people.",
                    "If you realize you are going to struggle with an issue(Ask for Help), To make an emotional connection with people while preparing and presenting information find(Evangilist, Bridge Builder)", //Empty if not exists
                    "Create a connection with individuals on an emotional level by listening and addressing how they are feeling about the new idea."
            ),
            Card(
                    5,
                    "Evolving Vision",
                    "", //Empty if not exist
                    "A lofty vision can seem attainable in the beginning, but can become unrealistic when the world changes during the process.",
                    "Consider involving impaitient people as(Champion Skeptics) during Milestone planing, If you or members of your team become discouraged find a(Shoulder to Cry On)", //Empty if not exists
                    "Use an iterative approach to learn about and refine your vision."
            ),
            Card(
                    6,
                    "Future Commitment",
                    "", //Empty if not exist
                    "You need help, but people are busy.",
                    "An agreement from everyone doesn´t guarantee that they follow through(Involve Everyone)", //Empty if not exists
                    "Approach individuals with an item that isn’t urgent so they can put it on their to-do list on a future date."
            ),
            Card(
                    7,
                    "Go-To Person",
                    "", //Empty if not exist
                    "Once you’ve identified areas where you lack expertise, how do you start asking for help?",
                    "Be careful on relying on just a few individuals", //Empty if not exists
                    "Make a concrete action plan with a list of the things you need to do for the next milestone. Next to each item, write the names of those individuals with the specific expertise or resources to help you accomplish the task."
            ),
            Card(
                    8,
                    "Imagine That",
                    "", //Empty if not exist
                    "It can be difficult for those you are trying to convince to see how a new idea will fit into the work they will be doing.",
                    "In addition to uncovering the positive outcomes of the change try to avoid an unrealistic future(Evolving Vision)", //Empty if not exists
                    "Ask people to imagine a possible outcome with the new idea.Begin with What if…?"
            ),
            Card(
                    9,
                    "Know Yourself",
                    "", //Empty if not exist
                    "How do you know if you should take on the role of an evangelist?",
                    "If you identify essential skills you don´t have, don´t give up work harder and look for(Evangelist,Ask for Help)", //Empty if not exists
                    "Set aside time for reflection to evaluate and understand your own abilities, limitations, and personal resources. Identify your values, principles, likes, dislikes, strengths, and weaknesses. Examine the beliefs and qualities that define who you are and what you will be able to do if you choose to lead this initiative. "
            ),
            Card(
                    10,
                    "Low Hanging Fruit",
                    "", //Empty if not exist
                    "Given all the tasks you have to accomplish in your change initiative, how do you decide which one to tackle when you feel pressure to make progress?",
                    "Take(Time for reflection) to determine what you can do after you have achived the easy wins, Spawn more(Evangelist) and work together to accomplish bigger things", //Empty if not exists
                    "As you prepare to move forward, occasionally look for a quick and easy win that will have visible impact."
            ),
            Card(
                    11,
                    "Myth Buster",
                    "", //Empty if not exist
                    "If we hear someone express an incorrect assumption about the innovation, we usually address it head-on with the person who is expressing the concern. However, a false impression in one person’s mind is usually a sign that this viewpoint is shared by others.",
                    "No one likes to be proven wrong and we tend to cling to our believes despite evidance to the contrary, create an(Emotional Connection) and use the(Fear Less) pattern", //Empty if not exists
                    "To get the word out about what the innovation isn’t as well as what won’t happen as a result of its introduction into the organization, create a simple list of the myths paired with the realities."
            ),
            Card(
                    12,
                    "Pick Your Battles",
                    "", //Empty if not exist
                    "You can’t spend time and energy addressing every bit of resistance you meet.",
                    "(Ask for help) when deciding which battles to avoid and create a backup plan for the possiblity that you made the wring choice, You might consider an effective(Champion Skeptic) to take the role of point out all the downsides of the new idea", //Empty if not exists
                    "Stop. Take a deep breath and think for a minute. Ask yourself if the current conflict is worth it. Overcome your initial emotional reaction and make a conscious decision to fight only for those things that will make a difference. Maintain your integrity so that at the end of each decision point you are proud of yourself"
            ),
            Card(
                    13,
                    "Town Hall Meeting",
                    "", //Empty if not exist
                    "It is difficult to stay in touch and involve everyone during the long period of time that is often necessary for a change initiative.",
                    "Be sure to set clear expectiations at the beginning of the meeting and remind everyone periodically", //Empty if not exists
                    "Hold a meeting to solicit feedback, build support, get new ideas, intrigue newcomers, and report progress."
            ),
            Card(
                    14,
                    "Wake-up Call",
                    "", //Empty if not exist
                    "People in your organization seem to be comfortable with the status quo. They don’t see the need to change the current state of things.",
                    "You´re not likly to get everyone to care about the problems, if too many people are not responding you may have to(Pick your battles) and move on", //Empty if not exists
                    "Create a conscious need for the change by calling attention to a problem and its negative consequences in the organization."
            ),
            Card(
                    15,
                    "Ask for Help",
                    "", //Empty if not exist
                    "The job of introducing a new idea into an organization is too big for one person, especially a newcomer who doesn’t know the ropes.",
                    "Asking for help can be seen as a sign of weakness, but you can overcome this matter by creating a constructive(Group Identity) and(Involve Everyone) who has contributed to the initiative", //Empty if not exists
                    "Ask as many people as you can for help when you need it. Don’t try to do it alone."
            ),
            Card(
                    16,
                    "Baby Steps (Step-by-step)",
                    "", //Empty if not exist
                    "You wonder what your plan should be for introducing the new idea into your organization.",
                    "Remain optimistic even if you take one step forewards and two steps back, find a(Shoulder to Cry On)", //Empty if not exists
                    "Use an incremental approach in the change initiative, with shortterm goals, while keeping your long-term vision."
            ),
            Card(
                    17,
                    "Big Jolt",
                    "", //Empty if not exist
                    "You’ve been carrying out some activities to give your new idea some visibility in your organization, but at some point you need to attract more attention to the effort.",
                    "The risk is that such events can create more enthusiasm than you can handle. Make sure you have people to help you after the speaker has gone(Ask for Help), Make sure this event is help in the context of a larger plan", //Empty if not exists
                    "Arrange for a high-profile person who can talk about the new idea to do a presentation in your organization."
            ),
            Card(
                    18,
                    "Bridge Builder",
                    "", //Empty if not exist
                    "Some won’t listen to even the most enthusiastic proponent if it’s someone they don’t know or trust.",
                    "", //Empty if not exists
                    "Ask for help from early adopters, connectors, or gurus who have already adopted the innovation. Introduce them to people who have interests similar to theirs and encourage them to discuss how they found the innovation useful."
            ),
            Card(
                    19,
                    "Brown Bag",
                    "", //Empty if not exist
                    "People can be too busy to attend optional meetings held during work hours.",
                    "Some cultures are not open to having meetings over lunch, make sure that people will accept the idea before you begin your plans", //Empty if not exists
                    "Hold the meeting in the middle of the day and invite attendees to bring their own lunches."
            ),
            Card(
                    20,
                    "Champion Skeptic ",
                    "", //Empty if not exist
                    "Some of the resisters to the new idea are strong opinion leaders in your organization. ",
                    "If the skeptics are a strong influence in the organization, be resilient and prepared to handle criticism", //Empty if not exists
                    "Ask for help from a skeptical opinion leader to play the role of “official skeptic” or “official realist.”"
            ),
            Card(
                    21,
                    "Connector",
                    "", //Empty if not exist
                    "Your organization is too big for you to personally contact everyone. ",
                    "Be aware of connectors who don´t support the innovation, Consider giving Connectors with a thumbs down attitude the specail role of(Champion Skeptic)", //Empty if not exists
                    "Ask for help in spreading the word about the innovation from those who know and communicate with many others in your organization. "
            ),
            Card(
                    22,
                    "Corporate Angel",
                    "",
                    "Support from local management will provide some attention and resources for the new idea, but you need high-level support to have a more lasting impact.",
                    "High-level can give the impression that the innovation is beeing imposed, if you suspect this could happen it may be better to concentrate on and growning more grassroots interest first(Plant the Seed)",
                    "Enlist the support of a high-level executive who has a special interest in the new idea and will provide direction and the resources to support it. "
            ),
            Card(
                    23,
                    "Corridor Politics",
                    "",
                    "It’s difficult to address the concerns of all decision makers when a new idea is raised in a large meeting.",
                    "The risk is that the people you talk with will expect a favour in the future",
                    "Informally work on decision makers and key influencers one-onone before the vote. Try to get the approval of anyone who can kill the idea. "
            ),

            Card(
                    24,
                    "Dedicated Champion",
                    "",
                    "Effectively introducing a new idea into any organization is too much work for a volunteer.",
                    "The role comes with the expectation to succeed, it becomes important for you to jusitfy you time and continually demonstrate the benefits",
                    "Make a case for including the change initiative as part of your job description. "
            ),
            Card(
                    25,
                    "Do Food",
                    "",
                    "Usually a meeting is just another ordinary, impersonal event.",
                    "When you begin to reguarly have food at event, people will expect it and be irritated when it doesn´t appear. If the food budget is depleted, have a (Brown Bag).",
                    "Make food available at the meeting."
            ),
            Card(
                    26,
                    "Early Adopter",
                    "",
                    "To create more impact for the new idea in an organization, interest must extend beyond the initial group of supporters.",
                    "Early Adopters will ask for more information before they become convinced, you should give them a short description at the beginning and provide more detailed information afterwards (Just Enough)",
                    "Look for the opinion leaders and ask them for help."
            ),
            Card(
                    27,
                    "Early Majority",
                    "",
                    "The support of innovators and early adopters will spark the new idea, but you need much more to truly have impact. ",
                    "You can become frustrated with this group, because they can be hard to reach by simply talking with them. Be patitent for the first (Smell of Success)",
                    "Expand the group that has adopted the new idea rapidly to include the more deliberate majority that will allow the new idea to establish a strong foothold. "
            ),
            Card(
                    28,
                    "Evangelist",
                    "",
                    "You want to get a new idea going, but you don’t know where to start. ",
                    "You can appear too passionate about the new idea and turn some people off. Keep in mind that most people need time before they feel the same enthusiasm you do.",
                    "To introduce a new idea, let your passion for this new idea drive"
            ),
            Card(
                    29,
                    "External Validation",
                    "",
                    "Before being persuaded to accept a new idea, people want assurance that the idea has validity outside the organization.",
                    "Sending books or articles can make people feel inadequate because they can´t keep up with the pace of reading. Use the (Just Enough) pattern.",
                    "Give people in the organization external sources of useful information about the new idea."
            ),
            Card(
                    30,
                    "Fear Less",
                    "",
                    "Any innovation is disruptive, so resistance is likely.",
                    "Resistors can overwhelm you if you are not prepared to handle criticism. Encourage them to talk with you one-on-one to protect yourself from a averbal attack in public. If the resistor is a high level person, use the (Corridor Politics) pattern.",
                    "Ask for help from resisters."
            ),
            Card(
                    31,
                    "Group Identity",
                    "",
                    "It’s harder to introduce a new idea when people aren’t aware that the effort exists. ",
                    "Buts",
                    "Give the change effort an identity."
            ),
            Card(
                    32,
                    "Guru on Your Side",
                    "",
                    "People in an organization can be reluctant to show interest in a new idea unless it has the support of colleagues they respect.",
                    "Buts",
                    "Enlist the support of experienced, senior-level gurus who are respected by both managers and non-managers alike. "
            ),
            Card(
                    33,
                    "Guru Review",
                    "",
                    "Some managers and developers are supportive, but others are reluctant to join in until they have some assurance that this is a worthwhile idea.",
                    "Buts",
                    "Gather a review team of respected gurus in the organization to evaluate the new idea."
            ),

            Card(
                    34,
                    "Hometown Story",
                    "",
                    "People who haven’t used the new idea may not be aware that other people have used it successfully.",
                    "Buts",
                    "Encourage individuals to share their experiences with the new idea in an informal, highly interactive session."
            ),
            Card(
                    35,
                    "Innovator",
                    "",
                    "You need people to jumpstart the new idea in your organization.",
                    "Buts",
                    "Find the people who are quick to adopt new ideas. Talk to them about the innovation and ask for help in sparking an interest for it in the organization. "
            ),
            Card(
                    36,
                    "Involve Everyone",
                    "",
                    "Even when you ask for help, there’s a tendency to take on too much. Others---especially those who don’t see the value in the new idea---may think of it as “your show.” ",
                    "Buts",
                    "Make it known that everyone is welcome to be part of the change effort. Involve people from as many different groups as possible: management, administrative and technical support, marketing, and training. "
            ),
            Card(
                    37,
                    "Just Do It",
                    "",
                    "You don’t have any experience with the innovation yourself, just good ideas that might work. You believe that the innovation can help the organization, but you’re not sure.",
                    "Buts",
                    "Gather firsthand information on the benefits and limitations ofthe innovation by integrating it into your current work."
            ),
            Card(
                    38,
                    "Just Enough",
                    "",
                    "Difficult, complex concepts can overwhelm novices.",
                    "Buts",
                    "When introducing the new idea, concentrate on the fundamentals and give learners a brief description of the more difficult concepts. Provide more information when they are ready"
            ),
            Card(
                    39,
                    "Local Sponsor",
                    "",
                    "You need attention and resources for the new idea. ",
                    "Buts",
                    "Find a first-line manager to support your new idea---ideally, your boss"
            ),
            Card(
                    40,
                    "Location, Location, Location",
                    "",
                    "When you hold an event onsite at the organization, attendees can be easily distracted with their nearby work obligations.",
                    "Buts",
                    "Hold significant events of a half-day or longer offsite but nearby."
            ),
            Card(
                    41,
                    "Mentor",
                    "",
                    "People want to use the new idea on their project but don’t know how to begin.",
                    "Buts",
                    "Find an outside or internal consultant or trainer to provide mentoring and feedback while project members are getting started with the innovation."
            ),
            Card(
                    42,
                    "Next Steps",
                    "",
                    "A presentation in a training class or another event can leave attendees uncertain about what to do with what they have learned.",
                    "Buts",
                    "Take time near the end of a presentation to brainstorm and discuss how the participants can apply the new information."
            ),
            Card(
                    43,
                    "Persistent PR",
                    "",
                    "Unless people are reminded, they may forget about the new idea.",
                    "Buts",
                    "Post information about the new idea around your organization wherever people are likely to see it and discuss it."
            ),
            Card(
                    44,
                    "Personal Touch",
                    "",
                    "Presentations and training will arouse curiosity and some interest in the new idea, but you must do more the old habits of most individuals will not die without effort.",
                    "Buts",
                    "Talk with individuals about the ways in which the new idea can be personally useful and valuable to them."
            ),
            Card(
                    45,
                    "Piggyback",
                    "",
                    "Several procedures or hurdles are required for the introduction of your new idea but you’re looking for an easier way.",
                    "Buts",
                    "Piggyback the new idea on a well-accepted practice in the organization."
            ),
            Card(
                    46,
                    "Plant the Seeds",
                    "",
                    "You want to spark some interest in the new idea.",
                    "Carrying lots of materials can be troublesome during travel.Some people could borrow materials but not return it afterwards. Use just enought and personal touch patterns to show how such information can be more useful",
                    "Carry materials about the new idea to events where people gather. Put them in places where people are likely to pick them up and look at them."
            ),
            Card(
                    47,
                    "The Right Time",
                    "",
                    "When people face deadlines and have too much to do, they tend to focus on things that move them toward completing necessary tasks and making the deadlines.",
                    "some people will inevitably claim to be busy when the task does not relate to their immediate job. Use Personal touch pattern to reach them",
                    "Be aware of those times when people are likely to be the busiest. Schedule events and requests for help outside those times."
            ),
            Card(
                    48,
                    "Royal Audience",
                    "",
                    "You want to get the most out of a visit from a famous person.",
                    "Some people can be upset for not being invited",
                    "Use spare hours or lunchtime during the day or evenings, before and/or after the featured presentation, to make the visitor available for teams, individuals, or managers."
            ),
            Card(
                    49,
                    "Shoulder to Cry On",
                    "",
                    "When you’re struggling to introduce a new idea, it’s easy to become discouraged.",
                    "Buts",
                    "Get together regularly with others who are also working to introduce the new idea or are interested in the process."
            ),
            Card(
                    50,
                    "Sincere Appreciation",
                    "",
                    "People feel unappreciated when they work hard and no one notices or cares.",
                    "Risk of offending people who feel left out",
                    "Find everyone who has helped you and say “thanks” in the most sincere way you can."
            ),
            Card(
                    51,
                    "Small Successes",
                    "",
                    "Every organizational change effort has its ups and downs. It’s a difficult process.",
                    "may upset jealous people and some may see celebration as a sign that effort is at an end",
                    "As you carry on in baby steps, take the time to recognize and celebrate successes, especially the small ones."
            ),
            Card(
                    52,
                    "Smell of Success",
                    "",
                    "When you start to have some success, newcomers will ask you about the innovation.",
                    "Can also attract people who have been megatively impacted by the new idea",
                    "When people comment on the success they see with the innovation, treat their inquiry as a teaching moment."
            ),
            Card(
                    53,
                    "Stay in Touch",
                    "",
                    "Your key supporters have too many things to think about and can forget about the new idea.",
                    "staying in touch takes work. persistent PR can help. ask connectors or local sponsors to help reach upper level managers",
                    "Stay in touch with your key supporters."
            ),
            Card(
                    54,
                    "Study Group",
                    "",
                    "There may be little or no money for formal training on the specific topic.",
                    "discovery process is not suitable for all types of learning. it is not suitable for all types of people. they are just one type of learning and should not be considered as a whole part of learning strategy",
                    "Form a group of no more than eight colleagues who are interested in exploring and studying an interesting topic."
            ),
            Card(
                    55,
                    "Sustained Momentum",
                    "",
                    "The many other things that need to be done will tempt you to put the task of introducing the new idea on the back burner for a while. Doing so can cause you and other people to lose interestin it.",
                    "maintaining a steady momentum on any project goes against the natural tendency to work",
                    "Take a proactive approach in the organization to the ongoingwork of sustaining the interest in the new idea. Take some smallaction each day, no matter how insignificant it may seem, tomove you closer to your goal."
            ),
            Card(
                    56,
                    "Tailor Made",
                    "",
                    "Individuals can be intrigued by interesting ideas, but to have impact on an organization, the idea has to be more than justinteresting.",
                    "its takes special effort to use this pattern. you need to research the need of the organization in order to match it with your idea ",
                    "Taylor your message about the innovation to the needs of the organization."
            ),
            Card(
                    57,
                    "Time for Reflection",
                    "",
                    "We make the same assumptions and the same mistakes based onthose assumptions over and over again.",
                    "failing to think about past and plan the next step can cause you to lose even more time in the long run",
                    "Pause in any activity to reflect on what is working well and whatshould be done differently."
            ),
            Card(
                    58,
                    "Token",
                    "",
                    "People may be enthusiastic about a topic when they first hearabout it, but the enthusiasm quickly wanes as they forget tomorrow what excited them today.",
                    "Buts",
                    "Hand out small tokens that will remind people of the new idea."
            ),
            Card(
                    59,
                    "Trial Run",
                    "",
                    "There are people in the organization who are expressing an endless stream of objections to the new idea. It would be adaunting, or even impossible, task to try to ease everyone’s worries before the new idea is adopted.",
                    "Buts",
                    "Suggest that the organization, or a segment of the organization,try the new idea for a limited period as an experiment."
            ),
            Card(
                    60,
                    "Whisper in the General’s Ear",
                    "",
                    "Managers who are against your new idea have the power toblock your progress.",
                    "Buts",
                    "Set up a short one-on-one meeting with a manager to addressany concerns with the innovation and the effort to introduce it."
            )
    )
}
