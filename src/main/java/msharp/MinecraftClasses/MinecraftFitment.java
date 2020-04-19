package msharp.MinecraftClasses;

import msharp.NotePopulation.FinalNote;

import java.util.ArrayList;
import java.util.List;

public class MinecraftFitment {
    public static NoteStructure fitToMinecraft(List<FinalNote> finalNotes){
        float songDuration = finalNotes.get(finalNotes.size()-1).getTiming();
        // todo calculate timing to tick with math.round instead
        int ticks = Math.round(songDuration*10+1);

        NoteStructure minecraftNotes = new NoteStructure();
        for(int i = 0 ; i < ticks; i++){
            ArrayList<MinecraftNote> notesThisTick = new ArrayList<>();

            // what notes should play this tick.
            while(finalNotes.size() > 0 && finalNotes.get(0).getTiming() <= i*0.1+0.05){
                notesThisTick.add(convertFinalNoteToMinecraftNote(finalNotes.get(0)));
                finalNotes.remove(0);
            }

            minecraftNotes.add(notesThisTick);
        }

        return minecraftNotes;
    }

    private static MinecraftNote convertFinalNoteToMinecraftNote(FinalNote finalNote) {



        return new MinecraftNote(finalNote.getInstrument(),finalNote.getInstrument().getPitch(finalNote.getTone(), finalNote.getOctave()));
    }
}