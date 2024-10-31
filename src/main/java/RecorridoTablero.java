public class RecorridoTablero {
	private int[][] tablero; 
	private int[][] solucion;
	private int fila; //Fila de la casilla de inicio
	private int col;  //Columna de la casilla de inicio
	private int n;   
	private int m;

	public RecorridoTablero(int[][] t, int fila, int col) {
		tablero = t;
		n = tablero.length;
		m = tablero[0].length;
		this.fila = fila;
		this.col = col;
		solucion = null;
	}

	public int resolverMemo() {
		// Creamos la tabla auxiliar
		solucion = new int[n][m]; // -1 representará que la celda está vacía.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				solucion[i][j] = -1;
			}
		}
		// Rellenamos la tabla desde la casilla indicada
		return resuelve(fila, col);
	}

	private int resuelve(int i, int j) {
		if(i==0){
			return tablero[i][j];
		}

		if(solucion[i][j]!=-1){
			return solucion[i][j];
		}

		int up = 0;
		if(j>=0){
			up=resuelve(i-1, j);
		}
		int diagonalIzq = 0;
		if(j>0){
			diagonalIzq=resuelve(i-1, j-1);
		}
		int diagonalDer = 0;
		if(j<m-1){
			diagonalDer=resuelve(i-1, j+1);
		}

		solucion[i][j] = tablero[i][j] + maximo3(up, diagonalIzq, diagonalDer);
		return solucion[i][j];
	}

	private int maximo3(int a, int b, int c) {
		int res = a;
		if (b > res) {
			res = b;
		}
		if (c > res) {
			res = c;
		}
		return res;
	}

	public Recorrido reconstruirSol() {
		if (solucion == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}

		Recorrido path = new Recorrido();
		int j = col;
		path.add(fila, j);

		for(int i = fila; i>0; i--){
			int up = -1;
			if(j>=0){
				up=resuelve(i-1, j);
			}
			int diagonalIzq = -1;
			if(j>0){
				diagonalIzq=resuelve(i-1, j-1);
			}
			int diagonalDer = -1;
			if(j<m-1){
				diagonalDer=resuelve(i-1, j+1);
			}

			if(up>=diagonalIzq&&up>=diagonalDer){
				path.add(i-1, j);
			} else if (diagonalIzq>=up&&diagonalIzq>=diagonalDer) {
				j--;
				path.add(i-1, j);
			}else{
				j++;
				path.add(i-1, j);
			}
		}
		return path;
	}

	public void imprimeMatrizSolucion() {
		if (solucion == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}
		for (int i = 0; i < solucion.length; i++) {
			for (int j = 0; j < solucion[i].length; j++) {
				System.out.print(solucion[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

}
