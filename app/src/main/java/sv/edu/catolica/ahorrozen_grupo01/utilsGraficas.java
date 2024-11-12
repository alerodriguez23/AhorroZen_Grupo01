package sv.edu.catolica.ahorrozen_grupo01;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class utilsGraficas {

    private List<Integer> colors;

    public utilsGraficas() {
        colors = new ArrayList<>();
        colors.add(Color.parseColor("#F44336"));
        colors.add(Color.parseColor("#E91E63"));
        colors.add(Color.parseColor("#9C27B0"));
        colors.add(Color.parseColor("#673AB7"));
        colors.add(Color.parseColor("#3F51B5"));
        colors.add(Color.parseColor("#2196F3"));
        colors.add(Color.parseColor("#009688"));
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.parseColor("#CDDC39"));
        colors.add(Color.parseColor("#FF9800"));
        colors.add(Color.parseColor("#FF5722"));
    }

    public int getRandomColor() {
        if (colors.isEmpty()) {
            throw new IllegalStateException("No more colors available");
        }
        int randomIndex = new Random().nextInt(colors.size());
        int color = colors.get(randomIndex);
        colors.remove(randomIndex);
        return color;
    }
}

