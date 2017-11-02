package ch.heigvd.wordoff.client.logic;

public class ChallengeTest {
  /*  static ArrayList initSlot;

    @BeforeClass
    public static void init() {
        initSlot = new ArrayList();
        initSlot.add(1);
        initSlot.add(2);
        initSlot.add(4);
        initSlot.add(1);
        initSlot.add(1);
        initSlot.add(4);
        initSlot.add(5);

    }

    @Test
    public void testGetSlots() {
        ChallengeDto ch = new ChallengeDto(new SideDto(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        ObservableList<SlotDto> chSlots = ch.getSlots();
        assertEquals(SlotDto.class, chSlots.get(0).getClass());
        assertEquals(L2SlotDto.class, chSlots.get(1).getClass());
        assertEquals(SwapSlotDto.class, chSlots.get(2).getClass());
        assertEquals(SlotDto.class, chSlots.get(3).getClass());
        assertEquals(SlotDto.class, chSlots.get(4).getClass());
        assertEquals(SwapSlotDto.class, chSlots.get(5).getClass());
        assertEquals(LastSlotDto.class, chSlots.get(6).getClass());
    }

    @Test
    public void testGetScore(){
        ChallengeDto ch = new ChallengeDto(new SideDto(), initSlot,new Dictionary(Constants.ENGLISH_DICTIONARY));
        ObservableList<SlotDto> chSlots = ch.getSlots();

        chSlots.get(0).addTile(new TileDto(0,'h',4));
        chSlots.get(1).addTile(new TileDto(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new TileDto(2,'l',1));
        chSlots.get(3).addTile(new TileDto(3,'l',1));
        chSlots.get(4).addTile(new TileDto(4,'o',1));

        assertEquals(9,ch.getScoreWord());

        chSlots.get(5).addTile(new TileDto(5,'s',1));

        assertEquals(10,ch.getScoreWord());
    }

    @Test
    public void testCheckWord(){
        ChallengeDto ch = new ChallengeDto(new SideDto(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        ObservableList<SlotDto> chSlots = ch.getSlots();
        chSlots.get(0).addTile(new TileDto(0,'h',4));
        chSlots.get(1).addTile(new TileDto(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new TileDto(2,'l',1));
        chSlots.get(3).addTile(new TileDto(3,'l',1));
        chSlots.get(4).addTile(new TileDto(4,'o',1));

        assertTrue(ch.checkWord());
        chSlots.get(5).addTile(new TileDto(5,'w',10));
        assertFalse(ch.checkWord());
    }

    @Test
    public void testGetSizeChallenge(){
        ChallengeDto ch = new ChallengeDto(new SideDto(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        assertEquals(7, ch.getSizeChallenge());
    }
    @Test
    public void testPlayTurn(){
        ChallengeDto ch = new ChallengeDto(new SideDto(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        ObservableList<SlotDto> chSlots = ch.getSlots();
        chSlots.get(0).addTile(new TileDto(0,'h',4));
        chSlots.get(1).addTile(new TileDto(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new TileDto(2,'l',1));
        chSlots.get(3).addTile(new TileDto(3,'l',1));
        chSlots.get(4).addTile(new TileDto(4,'o',1));

        assertTrue(ch.playTurn());
        chSlots.get(5).addTile(new TileDto(5,'w',10));
        assertFalse(ch.playTurn());
    }*/
}