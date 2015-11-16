package room.dao;

import java.util.Set;

import room.model.Room;

public interface RoomDAO {
	public Room insert(Room room);
	public Set<Room> findAllRoom();
	public Room findByRoomId(int roomId);
}
