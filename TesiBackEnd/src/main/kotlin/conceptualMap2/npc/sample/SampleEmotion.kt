package conceptualMap2.npc.sample

import conceptualMap2.npc.personality.Emotion

class SampleEmotion(name: String) : Emotion(name) {
    companion object{
        val HAPPY = SampleEmotion("happy")
        val HUNGRY = SampleEmotion("hungry")
    }
}