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
            Card(0,
                    "Elevator Pitch",
                    "elevator_pitch",
                    "When you have a chance to introduce someone to your idea, you don’t want to stumble around for the right words to say.",
                    "Buts...",
                    "Craft a couple of sentences that contain your key message."
            ),
            Card(1,
                    "Low Hanging Fruit",
                    "low_hanging_fruit",
                    "Given all the tasks you have to accomplish in your change initiative, how do you decide which one to tackle when you feel pressure to make progress?",
                    "To show progress in the change initiative, complete a quick and easy, low-risk task with wide impact and then publicize the results.",
                    "As you prepare to move forward, occasionally look for a quick and easy win that will have visible impact."
            ),
            Card(2,
                    "Easier Path",
                    "easier_path",
                    "What can you do to make it easier for people to change?",
                    "To encourage adoption of a new idea, experiment with removing obstacles that might be standing in the way.",
                    "Change the environment in a way that will encourage people to adopt the new idea."),
            Card(3,
                    "Piggyback",
                    "piggyback",
                    "Several procedures or hurdles are required for the introduction of your new idea but you’re looking for an easier way.",
                    "To help the new idea be less threatening, build on existing practices and use current language.",
                    "Piggyback the new idea on a well-accepted practice in the organization."),
            Card(4,
                    "Accentuate the Positive",
                    "accentuate_the_positive",
                    "Your attempts to scare others are not working.",
                    "To influence others during the change initiative and inspire them to believe the change can happen, motivate them with a sense of hope rather than fear.",
                    "Inspire people throughout the change initiative with a sense of optimism rather than fear."),
            Card(5,
                    "Concrete CardStatisticAction Plan",
                    "concrete_action_plan",
                    "Leading a change initiative, with its many twists and turns and ever-growing list of things to do, can make you feel out of control.",
                    "To make progress toward your goal, state precisely what you will do as you take the next baby step.",
                    "Describe the next small step for reaching a milestone goal in terms of concrete actions that include what you will do, where, and when."),
            Card(6,
                    "Emotional Connection",
                    "emotional_connection",
                    "As you share information about your new idea, you might believe that logical argument is enough to persuade people.",
                    "Connecting with the feelings of your audience is usually more effective in persuading them than just presenting facts.",
                    "Create a connection with individuals on an emotional level by listening and addressing how they are feeling about the new idea."),
            Card(7,
                    "Wake-up Call",
                    "wake-up_call",
                    "People in your organization seem to be comfortable with the status quo. They don’t see the need to change the current state of things.",
                    "To encourage people to pay attention to your idea, point out the issue that you believe has created a pressing need for change.",
                    "Create a conscious need for the change by calling attention to a problem and its negative consequences in the organization."),
            Card(8,
                    "Ask for help",
                    "ask_for_help",
                    "The job of introducing a new idea into an organization is too big for one person, especially a newcomer who doesn’t know the ropes.",
                    "Since the task of introducing a new idea into an organization is a big job, look for people and resources to help your efforts and encourage involvement.",
                    "Ask as many people as you can for help when you need it. Don’t try to do it alone."),
            Card(9,
                    "Baby steps",
                    "baby_steps",
                    "You wonder what your plan should be for introducing the new idea into your organization.",
                    "Take one small step at a time toward your goal.",
                    "Use an incremental approach in the change initiative, with shortterm goals, while keeping your long-term vision.")
    )

}
