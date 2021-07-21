package genius.management;

public enum SubFolder {
	Module("Modules"), Alt("Alts"), Other("Other");

	private final String folderName;

	private SubFolder(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderName() {
		return folderName;
	}
}