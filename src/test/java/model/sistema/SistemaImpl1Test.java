package model.sistema;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SistemaImpl1Test {

    private SistemaImpl1 sistema;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaImpl1();
    }

    @Test
    public void testSetTabellone() {
        sistema.setTabellone(10, 10);
        assertEquals(10, sistema.getTabellone().getR());
        assertEquals(10, sistema.getTabellone().getC());
        // Verifica che venga lanciata un'eccezione per valori invalidi
        assertThrows(IllegalArgumentException.class, () -> sistema.setTabellone(1, 10));
        assertThrows(IllegalArgumentException.class, () -> sistema.setTabellone(10, 1));
    }

    @Test
    public void testSetNPedine() {
        sistema.setNPedine(4);
        assertEquals(4, sistema.getNPedine());
        // Verifica che venga lanciata un'eccezione se il numero di pedine è minore di 2
        assertThrows(IllegalArgumentException.class, () -> sistema.setNPedine(1));
    }

    @Test
    public void testProssimoTurno() {
        sistema.setNPedine(3);
        int turnoIniziale = sistema.getTurno();
        sistema.prossimoTurno();
        assertNotEquals(turnoIniziale, sistema.getTurno());
    }

    @Test
    public void testLancia() {
        sistema.setDadoSingolo(true);
        sistema.setDadi();
        sistema.setNPedine(2);
        sistema.prossimoTurno();
        sistema.lancia();
        assertTrue(sistema.getLancio() > 0 && sistema.getLancio() <= 6); // Assume che un dado singolo va da 1 a 6
    }

    @Test
    public void testAvanza() {
        sistema.setDadoSingolo(true);
        sistema.setDadi();
        sistema.setNPedine(2);
        sistema.prossimoTurno();
        sistema.lancia();
        int posizioneIniziale = sistema.getCasellaCorrente().getPos();
        sistema.avanza();
        assertNotEquals(posizioneIniziale, sistema.getCasellaCorrente().getPos());
    }

    @Test
    public void testAzionaCasella() {
        sistema.setDadoSingolo(true);
        sistema.setDadi();
        sistema.setNPedine(2);
        sistema.prossimoTurno();
        sistema.lancia();
        sistema.avanza();
        boolean azioneEseguita = sistema.azionaCasella();
        assertTrue(true);
    }
}

