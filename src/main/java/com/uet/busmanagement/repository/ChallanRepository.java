package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Challan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChallanRepository
{

    // 1. Naya Challan database mien generate/save karne ke liye
    public boolean save(Challan challan)
    {
        String query = "INSERT INTO challans (challan_number, student_id, amount, due_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, challan.getChallanNumber());
            stmt.setInt(2, challan.getStudentId());
            stmt.setDouble(3, challan.getAmount());
            stmt.setString(4, challan.getDueDate());
            stmt.setString(5, challan.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Challan ka status update karne ke liye (e.g., Unpaid se Paid karna)
    public boolean updateStatus(String challanNumber, String status)
    {
        String query = "UPDATE challans SET status = ? WHERE challan_number = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, status);
            stmt.setString(2, challanNumber);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Kisi specific Student ke saare challans ki history nikalne ke liye
    public List<Challan> findByStudentId(int studentId)
    {
        List<Challan> challans = new ArrayList<>();
        String query = "SELECT * FROM challans WHERE student_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Challan challan = new Challan();
                challan.setChallanNumber(rs.getString("challan_number"));
                challan.setStudentId(rs.getShort("student_id"));
                challan.setAmount(rs.getDouble("amount"));
                challan.setDueDate(rs.getString("due_date"));
                challan.setStatus(rs.getString("status"));

                challans.add(challan);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return challans;
    }
}