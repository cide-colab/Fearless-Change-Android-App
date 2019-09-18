package de.thkoeln.colab.fearlesschange.persistance.pattern

import de.thkoeln.colab.fearlesschange.persistance.DataInitializer

class PatternInitializer : DataInitializer<Pattern>("pattern") {
    override fun getItemValues(item: Pattern?) = hashMapOf(
            "id" to item?.id,
            "title" to item?.title,
            "pictureName" to item?.pictureName,
            "problem" to item?.problem,
            "summary" to item?.summary,
            "solution" to item?.solution,
            "favorite" to item?.favorite
    )

    override fun getItems(): List<Pattern> = listOf(
            Pattern(
                    0,
                    "Accentuate the Positive",
                    "accentuate_the_positive", //Empty if not exist
                    "Your attempts to scare others are not working.",
                    "To influence others during the change initiative and inspire them to believe the change can happen, motivate them with a sense of hope rather than fear.",
                    "Inspire people throughout the change initiative with a sense of optimism rather than fear."
            ),
            Pattern(
                    1,
                    "Concrete Action Plan",
                    "concrete_action_plan",
                    "Leading a change initiative, with its many twists and turns and ever-growing list of things to do, can make you feel out of control.",
                    "To make progress toward your goal, state precisely what you will do as you take the next baby step.",
                    "Describe the next small step for reaching a milestone goal in terms of concrete actions that include what you will do, where, and when."
            ),
            Pattern(
                    2,
                    "Easier Path",
                    "easier_path",
                    "What can you do to make it easier for people to change?",
                    "To encourage adoption of a new idea, experiment with removing obstacles that might be standing in the way.",
                    "Change the environment in a way that will encourage people to adopt the new idea."
            ),
            Pattern(
                    3,
                    "Elevator Pitch",
                    "elevator_pitch", //Empty if not exist
                    "When you have a chance to introduce someone to your idea, you don’t want to stumble around for the right words to say.",
                    "Have a couple of sentences on hand to introduce others to your new idea.",
                    "Craft a couple of sentences that contain your key message."
            ),
            Pattern(
                    4,
                    "Emotional Connection",
                    "emotional_connection", //Empty if not exist
                    "As you share information about your new idea, you might believe that logical argument is enough to persuade people.",
                    "Connecting with the feelings of your audience is usually more effective in persuading them than just presenting facts.",
                    "Create a connection with individuals on an emotional level by listening and addressing how they are feeling about the new idea."
            ),
            Pattern(
                    5,
                    "Evolving Vision",
                    "evolving_vision", //Empty if not exist
                    "A lofty vision can seem attainable in the beginning, but can become unrealistic when the world changes during the process.",
                    "While taking baby steps through a change process,periodically set aside time for reflection to reevaluate your vision.",
                    "Use an iterative approach to learn about and refine your vision."
            ),
            Pattern(
                    6,
                    "Future Commitment",
                    "future_commitment", //Empty if not exist
                    "You need help, but people are busy.",
                    "To make it more likely that you will read help in the change initiative, ask others to do something you will need much later and wait for them to commit.",
                    "Approach individuals with an item that isn’t urgent so they can put it on their to-do list on a future date."
            ),
            Pattern(
                    7,
                    "Go-To Person",
                    "go_to_person", //Empty if not exist
                    "Once you’ve identified areas where you lack expertise, how do you start asking for help?",
                    "Identify key people who can help with critical issues in your change initiative.",
                    "Make a concrete action plan with a list of the things you need to do for the next milestone. Next to each item, write the names of those individuals with the specific expertise or resources to help you accomplish the task."
            ),
            Pattern(
                    8,
                    "Imagine That",
                    "imagine_that", //Empty if not exist
                    "It can be difficult for those you are trying to convince to see how a new idea will fit into the work they will be doing.",
                    "To kick-start the change initiative, engage others in an exercise to imagine future possibilities.",
                    "Ask people to imagine a possible outcome with the new idea.Begin with What if…?"
            ),
            Pattern(
                    9,
                    "Know Yourself",
                    "know_yourself", //Empty if not exist
                    "How do you know if you should take on the role of an evangelist?",
                    "Before you begin, and throughout the long journey required to lead a change initiative, consider whether you still have a real and abiding passion and the talents and abilities to make it happen.",
                    "Set aside time for reflection to evaluate and understand your own abilities, limitations, and personal resources. Identify your values, principles, likes, dislikes, strengths, and weaknesses. Examine the beliefs and qualities that define who you are and what you will be able to do if you choose to lead this initiative. "
            ),
            Pattern(
                    10,
                    "Low Hanging Fruit",
                    "low_hanging_fruit", //Empty if not exist
                    "Given all the tasks you have to accomplish in your change initiative, how do you decide which one to tackle when you feel pressure to make progress?",
                    "To show progress in the change initiative, complete a quick and easy, low-risk task with wide impact and then publicize the results.",
                    "As you prepare to move forward, occasionally look for a quick and easy win that will have visible impact."
            ),
            Pattern(
                    11,
                    "Myth Buster",
                    "myth_buster", //Empty if not exist
                    "If we hear someone express an incorrect assumption about the innovation, we usually address it head-on with the person who is expressing the concern. However, a false impression in one person’s mind is usually a sign that this viewpoint is shared by others.",
                    "Identify misconceptions surrounding the change initiative and address them in a timely and forthright manner.",
                    "To read the word out about what the innovation isn’t as well as what won’t happen as a result of its introduction into the organization, create a simple list of the myths paired with the realities."
            ),
            Pattern(
                    12,
                    "Pick Your Battles",
                    "pick_your_battles", //Empty if not exist
                    "You can’t spend time and energy addressing every bit of resistance you meet.",
                    "Before you expend your energy in conflict, ask yourself whether you believe the issue is really important and if you have the resources to carry your fight through to the end.",
                    "Stop. Take a deep breath and think for a minute. Ask yourself if the current conflict is worth it. Overcome your initial emotional reaction and make a conscious decision to fight only for those things that will make a difference. Maintain your integrity so that at the end of each decision point you are proud of yourself"
            ),
            Pattern(
                    13,
                    "Town Hall Meeting",
                    "town_hall_meeting", //Empty if not exist
                    "It is difficult to stay in touch and involve everyone during the long period of time that is often necessary for a change initiative.",
                    "As early as possible and throughout the initiative, schedule an event to share updates about the new idea, solicit feedback, build support, uncover new ideas, and bring in newcomers.",
                    "Hold a meeting to solicit feedback, build support, read new ideas, intrigue newcomers, and report progress."
            ),
            Pattern(
                    14,
                    "Wake-up Call",
                    "wake_up_call", //Empty if not exist
                    "People in your organization seem to be comfortable with the status quo. They don’t see the need to change the current state of things.",
                    "To encourage people to pay attention to your idea, point out the issue that you believe has created a pressing need for change.",
                    "Create a conscious need for the change by calling attention to a problem and its negative consequences in the organization."
            ),
            Pattern(
                    15,
                    "Ask for Help",
                    "ask_for_help", //Empty if not exist
                    "The job of introducing a new idea into an organization is too big for one person, especially a newcomer who doesn’t know the ropes.",
                    "Since the task of introducing a new idea into an organization is a big job, look for people and resources to help your efforts and encourage involvement.",
                    "Ask as many people as you can for help when you need it. Don’t try to do it alone."
            ),
            Pattern(
                    16,
                    "Baby Steps (Step-by-step)",
                    "baby_steps", //Empty if not exist
                    "You wonder what your plan should be for introducing the new idea into your organization.",
                    "Take one small step at a time toward your goal.",
                    "Use an incremental approach in the change initiative, with shortterm goals, while keeping your long-term vision."
            ),
            Pattern(
                    17,
                    "Big Jolt",
                    "big_jolt", //Empty if not exist
                    "You’ve been carrying out some activities to give your new idea some visibility in your organization, but at some point you need to attract more attention to the effort.",
                    "To provide visibility for the change effort, hold a highprofile event to showcase the new idea.",
                    "Arrange for a high-profile person who can talk about the new idea to do a presentation in your organization."
            ),
            Pattern(
                    18,
                    "Bridge Builder",
                    "bridge_builder", //Empty if not exist
                    "Some won’t listen to even the most enthusiastic proponent if it’s someone they don’t know or trust.",
                    "Ask those who have accepted the new idea to talk with those who have not.",
                    "Ask for help from early adopters, connectors, or gurus who have already adopted the innovation. Introduce them to people who have interests similar to theirs and encourage them to discuss how they found the innovation useful."
            ),
            Pattern(
                    19,
                    "Brown Bag",
                    "brown_bag", //Empty if not exist
                    "People can be too busy to attend optional meetings held during work hours.",
                    "Use the time when people normally eat together as a convenient and relaxed setting for hearing about the new idea.",
                    "Hold the meeting in the middle of the day and invite attendees to bring their own lunches."
            ),
            Pattern(
                    20,
                    "Champion Skeptic ",
                    "champion_skeptic", //Empty if not exist
                    "Some of the resisters to the new idea are strong opinion leaders in your organization. ",
                    "Ask for help from opinion leaders who are skeptical of your new idea, and use their comments to improve your effort, even if you don’t change their minds.",
                    "Ask for help from a skeptical opinion leader to play the role of “official skeptic” or “official realist.”"
            ),
            Pattern(
                    21,
                    "Connector",
                    "connector", //Empty if not exist
                    "Your organization is too big for you to personally contact everyone. ",
                    "To help you spread the word about the innovation, ask for help from people who have connections with many others in the organization.",
                    "Ask for help in spreading the word about the innovation from those who know and communicate with many others in your organization. "
            ),
            Pattern(
                    22,
                    "Corporate Angel",
                    "corporate_angel",
                    "Support from local management will provide some attention and resources for the new idea, but you need high-level support to have a more lasting impact.",
                    "To help align the innovation with the goals of the organization, read support from a high-level executive.",
                    "Enlist the support of a high-level executive who has a special interest in the new idea and will provide direction and the resources to support it. "
            ),
            Pattern(
                    23,
                    "Corridor Politics",
                    "corridor_politics",
                    "It’s difficult to address the concerns of all decision makers when a new idea is raised in a large meeting.",
                    "Informally work on decision makers and key influencers before an important vote, to ensure they understand the consequences of the decision.",
                    "Informally work on decision makers and key influencers one-onone before the vote. Try to read the approval of anyone who can kill the idea. "
            ),

            Pattern(
                    24,
                    "Dedicated Champion",
                    "dedicated_champion",
                    "Effectively introducing a new idea into any organization is too much work for a volunteer.",
                    "To increase your effectiveness in introducing your new idea, make a case for having the work become part of your job description.",
                    "Make a case for including the change initiative as part of your job description. "
            ),
            Pattern(
                    25,
                    "Do Food",
                    "do_food",
                    "Usually a meeting is just another ordinary, impersonal event.",
                    "To influence attendees, bring special food to a meeting.",
                    "Make food available at the meeting."
            ),
            Pattern(
                    26,
                    "Early Adopter",
                    "early_adopter",
                    "To create more impact for the new idea in an organization, interest must extend beyond the initial group of supporters.",
                    "Win the support of the people who can be opinion leaders for the new idea.",
                    "Look for the opinion leaders and ask them for help."
            ),
            Pattern(
                    27,
                    "Early Majority",
                    "early_majority",
                    "The support of innovators and early adopters will spark the new idea, but you need much more to truly have impact. ",
                    "To increase support, show that many people are starting to use the innovation.",
                    "Expand the group that has adopted the new idea rapidly to include the more deliberate majority that will allow the new idea to establish a strong foothold. "
            ),
            Pattern(
                    28,
                    "Evangelist",
                    "evangelist",
                    "You want to read a new idea going, but you don’t know where to start. ",
                    "To begin to introduce the new idea into your organization, do everything you can to share your passion for it.",
                    "To introduce a new idea, let your passion for this new idea drive"
            ),
            Pattern(
                    29,
                    "External Validation",
                    "external_validation",
                    "Before being persuaded to accept a new idea, people want assurance that the idea has validity outside the organization.",
                    "To increase the credibility of the new idea, bring in information from sources outside the organization.",
                    "Give people in the organization external sources of useful information about the new idea."
            ),
            Pattern(
                    30,
                    "Fear Less",
                    "fear_less",
                    "Any innovation is disruptive, so resistance is likely.",
                    "Turn resistance to the new idea to your advantage by respectfully listening to and learning from skeptics’ point of view.",
                    "Ask for help from resisters."
            ),
            Pattern(
                    31,
                    "Group Identity",
                    "group_identity",
                    "It’s harder to introduce a new idea when people aren’t aware that the effort exists. ",
                    "Give the change effort an identity but encourage wide participation to involve everyone.",
                    "Give the change effort an identity."
            ),
            Pattern(
                    32,
                    "Guru on Your Side",
                    "guru_on_your_side",
                    "People in an organization can be reluctant to show interest in a new idea unless it has the support of colleagues they respect.",
                    "Enlist the support of influential people who are esteemed by members of the organization at all levels.",
                    "Enlist the support of experienced, senior-level gurus who are respected by both managers and non-managers alike. "
            ),
            Pattern(
                    33,
                    "Guru Review",
                    "guru_review",
                    "Some managers and developers are supportive, but others are reluctant to join in until they have some assurance that this is a worthwhile idea.",
                    "Gather a group of trusted advisors and other interested colleagues to evaluate the new idea for managers and other developers.",
                    "Gather a review team of respected gurus in the organization to evaluate the new idea."
            ),

            Pattern(
                    34,
                    "Hometown Story",
                    "hometown_story",
                    "People who haven’t used the new idea may not be aware that other people have used it successfully.",
                    "To help people see the usefulness of the new idea, encourage those who have had success with it to share their stories in an informal setting.",
                    "Encourage individuals to share their experiences with the new idea in an informal, highly interactive session."
            ),
            Pattern(
                    35,
                    "Innovator",
                    "innovator",
                    "You need people to jumpstart the new idea in your organization.",
                    "When you begin the change initiative, ask for help from colleagues who like new ideas.",
                    "Find the people who are quick to adopt new ideas. Talk to them about the innovation and ask for help in sparking an interest for it in the organization. "
            ),
            Pattern(
                    36,
                    "Involve Everyone",
                    "involve_everyone",
                    "Even when you ask for help, there’s a tendency to take on too much. Others---especially those who don’t see the message in the new idea---may think of it as “your show.” ",
                    "For a new idea to be successful across an organization, everyone should have an opportunity to make his or her own unique contribution.",
                    "Make it known that everyone is welcome to be part of the change effort. Involve people from as many different groups as possible: management, administrative and technical support, marketing, and training. "
            ),
            Pattern(
                    37,
                    "Just Do It",
                    "just_do_it",
                    "You don’t have any experience with the innovation yourself, just good ideas that might work. You believe that the innovation can help the organization, but you’re not sure.",
                    "Don’t wait for the perfect moment when you have the resources and knowledge you think you need; instead, take the first baby step and start learning.",
                    "Gather firsthand information on the benefits and limitations ofthe innovation by integrating it into your current work."
            ),
            Pattern(
                    38,
                    "Just Enough",
                    "just_enough",
                    "Difficult, complex concepts can overwhelm novices.",
                    "To ease people into the new idea, avoid over-selling and overwhelming them by providing an appropriate amount of information that they can understand and use at that particular time.",
                    "When introducing the new idea, concentrate on the fundamentals and give learners a brief description of the more difficult concepts. Provide more information when they are ready"
            ),
            Pattern(
                    39,
                    "Local Sponsor",
                    "local_sponsor",
                    "You need attention and resources for the new idea. ",
                    "Ask for help from first-line management; when your boss supports the tasks you are doing to introduce the new idea, you can be more effective.",
                    "Find a first-line manager to support your new idea---ideally, your boss"
            ),
            Pattern(
                    40,
                    "Location, Location, Location",
                    "location",
                    "When you hold an event onsite at the organization, attendees can be easily distracted with their nearby work obligations.",
                    "When holding an event that focuses on the new idea, consider the comfort and enjoyment of the participants so the surroundings do not interfere with their ability to listen and participate.",
                    "Hold significant events of a half-day or longer offsite but nearby."
            ),
            Pattern(
                    41,
                    "Mentor",
                    "mentor",
                    "People want to use the new idea on their project but don’t know how to begin.",
                    "When a project team wants to read started with the new idea, have someone around who understands it and can help the team.",
                    "Find an outside or internal consultant or trainer to provide mentoring and feedback while project members are getting started with the innovation."
            ),
            Pattern(
                    42,
                    "Next Steps",
                    "next_steps",
                    "A presentation in a training class or another event can leave attendees uncertain about what to do with what they have learned.",
                    "Take time near the end of an event or conversation to identify which actions participants can do next.",
                    "Take time near the end of a presentation to brainstorm and discuss how the participants can apply the new information."
            ),
            Pattern(
                    43,
                    "Persistent PR",
                    "persistent_pr",
                    "Unless people are reminded, they may forget about the new idea.",
                    "To keep the new idea in front of everyone, consistently promote it in a variety of ways.",
                    "Post information about the new idea around your organization wherever people are likely to see it and discuss it."
            ),
            Pattern(
                    44,
                    "Personal Touch",
                    "personal_touch",
                    "Presentations and training will arouse curiosity and some interest in the new idea, but you must do more the old habits of most individuals will not die without effort.",
                    "To convince people of the message in a new idea, show how it can be personally useful and valuable to them.",
                    "Talk with individuals about the ways in which the new idea can be personally useful and valuable to them."
            ),
            Pattern(
                    45,
                    "Piggyback",
                    "piggyback",
                    "Several procedures or hurdles are required for the introduction of your new idea but you’re looking for an easier way.",
                    "To help the new idea be less threatening, build on existing practices and use current language.",
                    "Piggyback the new idea on a well-accepted practice in the organization."
            ),
            Pattern(
                    46,
                    "Plant the Seeds",
                    "plant_the_seeds",
                    "You want to spark some interest in the new idea.",
                    "Take every opportunity you can, no matter how small, to spark an interest in the idea.",
                    "Carry materials about the new idea to events where people gather. Put them in places where people are likely to pick them up and look at them."
            ),
            Pattern(
                    47,
                    "The Right Time",
                    "the_right_time",
                    "When people face deadlines and have too much to do, they tend to focus on things that move them toward completing necessary tasks and making the deadlines.",
                    "Consider the timing of competing obligations when you schedule events or when you ask for help.",
                    "Be aware of those times when people are likely to be the busiest. Schedule events and requests for help outside those times."
            ),
            Pattern(
                    48,
                    "Royal Audience",
                    "royal_audience",
                    "You want to read the most out of a visit from a famous person.",
                    "Arrange for management and members of the organization to spend time with a special visitor.",
                    "Use spare hours or lunchtime during the day or evenings, before and/or after the featured presentation, to make the visitor available for teams, individuals, or managers."
            ),
            Pattern(
                    49,
                    "Shoulder to Cry On",
                    "shoulder_to_cry_on",
                    "When you’re struggling to introduce a new idea, it’s easy to become discouraged.",
                    "To avoid becoming too discouraged when the going gets tough, find opportunities for everyone to have supportive listeners.",
                    "Get together regularly with others who are also working to introduce the new idea or are interested in the process."
            ),
            Pattern(
                    50,
                    "Sincere Appreciation",
                    "thank_you",
                    "People feel unappreciated when they work hard and no one notices or cares.",
                    "To help people feel appreciated, express your gratitude in the most sincere way you can to everyone who makes a contribution.",
                    "Find everyone who has helped you and say “thanks” in the most sincere way you can."
            ),
            Pattern(
                    51,
                    "Small Successes",
                    "small_success",
                    "Every organizational change effort has its ups and downs. It’s a difficult process.",
                    "To avoid becoming discouraged by obstacles and slow progress, celebrate even a small success.",
                    "As you carry on in baby steps, take the time to recognize and celebrate successes, especially the small ones."
            ),
            Pattern(
                    52,
                    "Smell of Success",
                    "smell_of_success",
                    "When you start to have some success, newcomers will ask you about the innovation.",
                    "When your efforts produce a visibly positive result, treat this opportunity as a teaching moment.",
                    "When people comment on the success they see with the innovation, treat their inquiry as a teaching moment."
            ),
            Pattern(
                    53,
                    "Stay in Touch",
                    "stay_in_touch",
                    "Your key supporters have too many things to think about and can forget about the new idea.",
                    "Once you’ve sparked some interest in people, don’t forget about them, and make sure they don’t forget about you.",
                    "Stay in touch with your key supporters."
            ),
            Pattern(
                    54,
                    "Study Group",
                    "study_group",
                    "There may be little or no money for formal training on the specific topic.",
                    "Form a small group of colleagues who are interested in exploring or continuing to learn about your new idea.",
                    "Form a group of no more than eight colleagues who are interested in exploring and studying an interesting topic."
            ),
            Pattern(
                    55,
                    "Sustained Momentum",
                    "sustained_momentum",
                    "The many other things that need to be done will tempt you to put the task of introducing the new idea on the back burner for a while. Doing so can cause you and other people to lose interestin it.",
                    "Be proactive in keeping your change initiative going.",
                    "Take a proactive approach in the organization to the ongoingwork of sustaining the interest in the new idea. Take some smallaction each day, no matter how insignificant it may seem, tomove you closer to your goal."
            ),
            Pattern(
                    56,
                    "Tailor Made",
                    "tailor_made",
                    "Individuals can be intrigued by interesting ideas, but to have impact on an organization, the idea has to be more than justinteresting.",
                    "To convince management and executives in the organization, point out the costs and benefits of your new idea. ",
                    "Taylor your message about the innovation to the needs of the organization."
            ),
            Pattern(
                    57,
                    "Time for Reflection",
                    "self_reflection",
                    "We make the same assumptions and the same mistakes based onthose assumptions over and over again.",
                    "To learn from the past, take time at regular intervals to evaluate what is working well and what should be done differently.",
                    "Pause in any activity to reflect on what is working well and whatshould be done differently."
            ),
            Pattern(
                    58,
                    "Token",
                    "token",
                    "People may be enthusiastic about a topic when they first hearabout it, but the enthusiasm quickly wanes as they forget tomorrow what excited them today.",
                    "To keep a new idea alive in a person’s memory, give tokens, especially valuable intangibles that can be identified with the topic being introduced.",
                    "Hand out small tokens that will remind people of the new idea."
            ),
            Pattern(
                    59,
                    "Trial Run",
                    "train_run",
                    "There are people in the organization who are expressing an endless stream of objections to the new idea. It would be adaunting, or even impossible, task to try to ease everyone’s worries before the new idea is adopted.",
                    "When the organization is reluctant to commit to the new idea, suggest an experiment for a short period and learn from its results.",
                    "Suggest that the organization, or a segment of the organization,try the new idea for a limited period as an experiment."
            ),
            Pattern(
                    60,
                    "Whisper in the General’s Ear",
                    "whisper_in_the_generals_ear",
                    "Managers who are against your new idea have the power toblock your progress.",
                    "Because managers and others at any level of authority are usually hard to convince in a group setting, meet privately to address any concerns.",
                    "Set up a short one-on-one meeting with a manager to addressany concerns with the innovation and the effort to introduce it."
            )
    )
}
