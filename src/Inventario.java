public class Inventario {
    private int pan = 4;
    private int carne = 7;
    private int lechuga = 8;
    private int queso = 6;
    private int tocino = 5;
    private int tomate = 3;

    public int getPan() { return pan; }
    public void setPan(int pan) { this.pan = pan; }

    public int getCarne() { return carne; }
    public void setCarne(int carne) { this.carne = carne; }

    public int getLechuga() { return lechuga; }
    public void setLechuga(int lechuga) { this.lechuga = lechuga; }

    public int getQueso() { return queso; }
    public void setQueso(int queso) { this.queso = queso; }

    public int getTocino() { return tocino; }
    public void setTocino(int tocino) { this.tocino = tocino; }

    public int getTomate() { return tomate; }
    public void setTomate(int tomate) { this.tomate = tomate; }


    public void recargarInventario() {
        this.pan = 4;
        this.carne = 7;
        this.lechuga = 8;
        this.queso = 6;
        this.tocino = 5;
        this.tomate = 3;
    }
}

